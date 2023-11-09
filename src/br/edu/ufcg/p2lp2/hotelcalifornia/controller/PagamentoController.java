package br.edu.ufcg.p2lp2.hotelcalifornia.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos.Cartao;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos.Dinheiro;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos.FormaPagamento;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos.Pagamento;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos.Pix;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos.TipoFormaPagamento;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.Reserva;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

public class PagamentoController {

	private static PagamentoController instance;
	private Map<Integer, FormaPagamento> tipoPagamentos;
	private Map<String, Pagamento> pagamentos;
	private int idPagamento;
	
	private PagamentoController() {
		this.tipoPagamentos = new HashMap<>();
		this.pagamentos = new HashMap<>();
		this.idPagamento = 0;
	}
	
	public static PagamentoController getInstance() {
		if (instance == null) {
			instance = new PagamentoController();
		}
		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}
	
	public void init() {
		this.tipoPagamentos.clear();
		this.pagamentos.clear();
		this.idPagamento = 0;
	}
	
	public String disponibilizarFormaDePagamento(UsuarioController userController, String idAutenticacao, String formaPagamento, double percentualDesconto){
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoDisponibilizaFormaDePagamento();
		FormaPagamento formaPag = null;
		this.idPagamento +=1;
		for (FormaPagamento tp : tipoPagamentos.values()){
			if (tp.getFormaPagamento().equals(formaPagamento)){
				throw new HotelCaliforniaException("FORMA DE PAGAMENTO JA EXISTE");
			}
		}
		if (formaPagamento.equalsIgnoreCase("DINHEIRO")){
			formaPag = new FormaPagamento(this.idPagamento+"", TipoFormaPagamento.DINHEIRO, percentualDesconto);
		}
		else if (formaPagamento.equalsIgnoreCase("CARTAO DE CREDITO") || formaPagamento.equalsIgnoreCase("CARTAO_DE_CREDITO")){
			formaPag = new FormaPagamento(this.idPagamento+"", TipoFormaPagamento.CARTAO, percentualDesconto);
		} else if (formaPagamento.equalsIgnoreCase("PIX")) {
			formaPag = new FormaPagamento(this.idPagamento+"", TipoFormaPagamento.PIX, percentualDesconto);
		} else {
			throw new HotelCaliforniaException("Forma de pagamento não definida");
		}
		this.tipoPagamentos.put(this.idPagamento, formaPag);
		return formaPag.toString();
	}

	public String alterarFormaDePagamento(UsuarioController userController, String idAutenticacao, int idFormaPagamento, String formaPagamento, double percentualDesconto){
		userController.getUsuario(idAutenticacao).getPatente().permissaoAlteraFormaPagamento();
		FormaPagamento formaAlterar = this.tipoPagamentos.get(idFormaPagamento);
		formaAlterar.setFormaPagamento(formaPagamento);
		formaAlterar.alterarDesconto(percentualDesconto);
		return formaAlterar.toString();
	}

	public String exibirFormaPagamento(int idFormaPagamento){
		return this.tipoPagamentos.get(idFormaPagamento).toString();
	}

	public String[] listarFormasPagamentos(){
		String[] formas = new String[tipoPagamentos.size()];
		int cont = 0;
		for (FormaPagamento fp : tipoPagamentos.values()){
			formas[cont] = fp.toString();
			cont +=1;
		}
		return formas;
	}

	public FormaPagamento retornaFormaPagamento(String formaDesejada){
		for (FormaPagamento fp : tipoPagamentos.values()){
			if (fp.getFormaPagamento().equals(formaDesejada)){
				return fp;
			}
		}
		throw new HotelCaliforniaException("Forma de pagamento não esperada");
	}

	public double valorReservaCliente(Reserva reserva){
		Reserva reservaCliente = reserva;
		int qntdDias = (int) Duration.between(reservaCliente.getDataInicio(), reservaCliente.getDataFinal()).toDays();
		return qntdDias * reservaCliente.valorTotal();
	}
	
	public String pagarReservaComDinheiro(UsuarioController userController, String idCliente, ReservasSessionController reservaController, long idReserva, String nomeTitular) {
		userController.existeUsuario(idCliente);
		if (!(idCliente.contains("CLI"))) {
			throw new HotelCaliforniaException("O USUARIO NAO E CLIENTE");
		}
		Reserva r = reservaController.getReserva(idReserva);
		Usuario cliente = r.getCliente();
		FormaPagamento forma = retornaFormaPagamento("DINHEIRO");
		if (!(cliente.getNome().equals(nomeTitular))) {
			throw new HotelCaliforniaException("SOMENTE O PROPRIO CLIENTE PODERA PAGAR A SUA RESERVA");
		}
		if (!(r.getSituacaoPagamento().equals("PENDENTE"))) {
			throw new HotelCaliforniaException("RESERVA JA FOI PAGA");
		}
		r.setSituacaoPagamento("REALIZADO");
		Pagamento pagamento = new Dinheiro(r, cliente, forma, valorReservaCliente(r), nomeTitular);
		r.setPagamento(pagamento);
		r.setSituacaoPagamento(situacaoPagamentoAtualizada(r));
		pagamentos.put(r.getId()+"", pagamento);
		return r.toString();
	}

	private boolean verificaFormato(String data) {
		String regex = "\\d{2}/\\d{4}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		return matcher.matches();
	}

	private void verificaValidadeCartao(String idCliente, String validade, String codigoDeSeguranca, String numCartao, int qtdeParcelas){
		if (!(idCliente.contains("CLI"))) {
			throw new HotelCaliforniaException("O USUARIO NAO E CLIENTE");
		}
		if (!(verificaFormato(validade))) {
			throw new HotelCaliforniaException("MES E ANO DE VALIDADE INVALIDOS");
		}
		if (!(codigoDeSeguranca.length() == 3)) {
			throw new HotelCaliforniaException("CVC INVALIDO");
		}
		if (!(numCartao.length() == 16)) {
			throw new HotelCaliforniaException("NUMERO DE CARTAO INVALIDO");
		}
		if (qtdeParcelas > 12){
			throw new HotelCaliforniaException("QUANTIDADE DE PARCELAS NAO PERMITIDA");
		}
	}

	public String pagarReservaComCartao(UsuarioController userController, String idCliente, ReservasSessionController reservaController, long idReserva, String nomeTitular, String numCartao, String validade, String codigoDeSeguranca, int qtdeParcelas) {
		userController.existeUsuario(idCliente);
		verificaValidadeCartao(idCliente, validade, codigoDeSeguranca, numCartao, qtdeParcelas);
		Reserva r = reservaController.getReserva(idReserva);
		Usuario cliente = r.getCliente();
		FormaPagamento forma = retornaFormaPagamento("CARTAO");
		if (!(cliente.getIdUser().equals(idCliente))) {
			throw new HotelCaliforniaException("SOMENTE O PROPRIO CLIENTE PODERA PAGAR A SUA RESERVA");
		}
		if (!(r.getSituacaoPagamento().equals("PENDENTE"))) {
			throw new HotelCaliforniaException("RESERVA JA FOI PAGA");
		}
		r.setSituacaoPagamento("REALIZADO");
		Pagamento pagamento = new Cartao(r, cliente, forma, nomeTitular, valorReservaCliente(r), numCartao, validade, codigoDeSeguranca, qtdeParcelas);
		pagamentos.put(r.getId()+"", pagamento);
		r.setPagamento(pagamento);
		r.setSituacaoPagamento(situacaoPagamentoAtualizada(r));
		return r.toString();
	}

	public String pagarReservaComPix(UsuarioController userController, String idCliente, ReservasSessionController reservaController, long idReserva, String nomeTitular, String cpf, String banco){
		userController.existeUsuario(idCliente);
		if (!(idCliente.contains("CLI"))) {
			throw new HotelCaliforniaException("O USUARIO NAO E CLIENTE");
		}
		if (!(cpf.length() == 11)) {
			throw new HotelCaliforniaException("O CPF não é válido");
		}
		Reserva r = reservaController.getReserva(idReserva);
		Usuario cliente = r.getCliente();
		FormaPagamento forma = retornaFormaPagamento("PIX");
		if (!(cliente.getNome().equals(nomeTitular))) {
			throw new HotelCaliforniaException("SOMENTE O PROPRIO CLIENTE PODERA PAGAR A SUA RESERVA");
		}
		if (!(r.getSituacaoPagamento().equals("PENDENTE"))) {
			throw new HotelCaliforniaException("RESERVA JA FOI PAGA");
		}
		r.setSituacaoPagamento("REALIZADO");
		Pagamento pagamento = new Pix(r, cliente, forma, valorReservaCliente(r), nomeTitular, cpf, banco);
		pagamentos.put(r.getId()+"", pagamento);
		r.setPagamento(pagamento);
		r.setSituacaoPagamento(situacaoPagamentoAtualizada(r));
		return r.toString();
	}

	public String situacaoPagamentoAtualizada(Reserva reserva){
		StringBuilder sb = new StringBuilder();
		if (reserva.getSituacaoPagamento().equals("REALIZADO")) {
			sb.append("REALIZADO.\n");
			sb.append(reserva.getPagamento().getFormaPagamento().toString()).append(".\n");
			sb.append((reserva.getPagamento().toString()));
			return sb.toString();
		}
		return "PENDENTE";
	}
}