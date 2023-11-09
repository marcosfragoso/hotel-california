package br.edu.ufcg.p2lp2.hotelcalifornia;

import br.edu.ufcg.p2lp2.hotelcalifornia.controller.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class HotelCaliforniaSistema {
	private UsuarioController usuarioController;
	private QuartoController quartoController;
	private ReservasSessionController reservasSessionController;
	private RefeicaoController refeicaoController;
	private PagamentoController pagamentoController;
	private AreaComumController areaComumController;

	public HotelCaliforniaSistema() {
		this.pagamentoController = PagamentoController.getInstance();
		this.pagamentoController.init();
		this.quartoController = QuartoController.getInstance();
		this.quartoController.init();
		this.refeicaoController = RefeicaoController.getInstance();
		this.refeicaoController.init();
		this.reservasSessionController = ReservasSessionController.getInstance();
		this.reservasSessionController.init();
		this.usuarioController = UsuarioController.getInstance();
		this.usuarioController.init();
		this.areaComumController = AreaComumController.getInstance();
		this.areaComumController.init();
	}

	public String cadastrarUsuario(String idAutenticacao, String nome, String tipoUsuario, long documento) {
		return this.usuarioController.cadastrarUsuario(idAutenticacao, nome, tipoUsuario, documento);
	}
	
	public String atualizarUsuario(String idAutenticacao, String idUsuario, String novoTipoUsuario) {
		return this.usuarioController.atualizarUsuario(idAutenticacao, idUsuario, novoTipoUsuario);
	}

	public String exibirUsuario(String idUsuario){
		return this.usuarioController.exibirUsuario(idUsuario);
	}
 	public String[] listarUsuarios(){
		return this.usuarioController.listarUsuarios();
	}

	public String disponibilizarQuartoSingle(String idAutenticacao, int idQuartoNum, double precoPorPessoa, double precoBase) {
		return this.quartoController.disponibilizarQuartoSingle(this.usuarioController, idAutenticacao, idQuartoNum, precoBase, precoPorPessoa);
	}

	public String disponibilizarQuartoDouble(String idAutenticacao, int idQuartoNum, double precoPorPessoa, double precoBase, String[] pedidos) {
		return this.quartoController.disponibilizarQuartoDouble(this.usuarioController, idAutenticacao, idQuartoNum, precoBase, precoPorPessoa, pedidos);
	}

	public String disponibilizarQuartoFamily(String idAutenticacao, int idQuartoNum, double precoPorPessoa, double precoBase, String[] pedidos, int qntdMaxPessoas) {
		return this.quartoController.disponibilizarQuartoFamily(this.usuarioController, idAutenticacao, idQuartoNum, precoBase, precoPorPessoa, pedidos, qntdMaxPessoas);
	}

	public String exibirQuarto(int idQuartoNum) {
		return this.quartoController.exibirQuarto(idQuartoNum);
	}

	public String[] listarQuartos(){
		return this.quartoController.listarQuartos();
	}

	public String reservarQuartoSingle(String idAutenticacao, String idCliente, int idNumQuarto, LocalDateTime dataInicio, LocalDateTime dataFim, String[] idRefeicoes){
		return this.reservasSessionController.reservarQuartoSingle(this.usuarioController, idAutenticacao, idCliente, this.quartoController, idNumQuarto, dataInicio, dataFim, this.refeicaoController, idRefeicoes);			
	}

	public String reservarQuartoDouble(String idAutenticacao, String idCliente, int idNumQuarto, LocalDateTime dataInicio, LocalDateTime dataFim, String[] idRefeicoes, String[] teste){
		return this.reservasSessionController.reservarQuartoDouble(this.usuarioController, idAutenticacao, idCliente, this.quartoController, idNumQuarto, dataInicio, dataFim, this.refeicaoController, idRefeicoes, teste);
	}

	public String reservarQuartoFamily(String idAutenticacao, String idCliente, int idNumQuarto, LocalDateTime dataInicio, LocalDateTime dataFim, String[] idRefeicoes, String[] teste, int qntdPessoas){
		return this.reservasSessionController.reservarQuartoFamily(this.usuarioController, idAutenticacao, idCliente, this.quartoController, idNumQuarto, dataInicio, dataFim, this.refeicaoController, idRefeicoes, teste, qntdPessoas);
	}

	public String exibirReserva(String idAutenticacao, long idReserva) {
		return this.reservasSessionController.exibirReserva(this.usuarioController, idAutenticacao, idReserva);
	}
	
	public String disponibilizarRefeicao(String idAutenticacao, String tipoRefeicao, String titulo, LocalTime horarioInicio, LocalTime horarioFinal, double valor, boolean disponivel) {
		return this.refeicaoController.disponibilizarRefeicao(this.usuarioController, idAutenticacao, tipoRefeicao, titulo, horarioInicio, horarioFinal, valor, disponivel);
	}
	
	public String alterarRefeicao(long idRefeicao, LocalTime horarioInicio, LocalTime horarioFinal, double valor, boolean disponivel) {
		return this.refeicaoController.alterarRefeicao(idRefeicao, horarioInicio, horarioFinal, disponivel, valor);	
	}
	
	public String exibirRefeicao(long idRefeicao) {
		return this.refeicaoController.exibirRefeicao(idRefeicao);
	}

	public String[] listarRefeicoes() {
		return this.refeicaoController.listarRefeicoes();
	}
	
	public String reservarRestaurante(String idAutenticacao, String idCliente, LocalDateTime dataInicio, LocalDateTime dataFim, int qtdePessoas, String refeicao) {
		return this.reservasSessionController.reservarRestaurante(this.usuarioController, idAutenticacao, idCliente, dataInicio, dataFim, qtdePessoas, this.refeicaoController, refeicao);
	}

	public String[] listarReservasAtivasDoCliente(String idAutenticacao, String idCliente) {
		return this.reservasSessionController.listarReservasAtivasDoCliente(this.usuarioController, idAutenticacao, idCliente);
	}

	public String[] listarReservasAtivasDoClientePorTipo(String idAutenticacao, String idCliente, String tipo) {
		return this.reservasSessionController.listarReservasAtivasDoClientePorTipo(this.usuarioController, idAutenticacao, idCliente, tipo);
	}

	public String[] listarReservasAtivasPorTipo(String idAutenticacao, String tipo) {
		return this.reservasSessionController.listarReservasAtivasPorTipo(this.usuarioController, idAutenticacao, tipo);
	}

	public String[] listarReservasAtivas(String idAutenticacao) {
		return this.reservasSessionController.listarReservasAtivas(this.usuarioController, idAutenticacao);
	}

	public String[] listarReservasTodas(String idAutenticacao) {
		return this.reservasSessionController.listarReservasTodas(this.usuarioController, idAutenticacao);
	}

	public String disponibilizarFormaDePagamento(String idAutenticacao, String formaPagamento, double percentualDesconto){
		return this.pagamentoController.disponibilizarFormaDePagamento(this.usuarioController, idAutenticacao, formaPagamento, percentualDesconto);
	}

	public String alterarFormaDePagamento(String idAutenticacao, int idFormaPagamento, String formaPagamento, double percentualDesconto){
		return this.pagamentoController.alterarFormaDePagamento(this.usuarioController, idAutenticacao, idFormaPagamento, formaPagamento, percentualDesconto);
	}

	public String exibirFormaPagamento(int idFormaPagamento){
		return this.pagamentoController.exibirFormaPagamento(idFormaPagamento);
	}

	public String[] listarFormasPagamentos(){
		return this.pagamentoController.listarFormasPagamentos();
	}

	public String pagarReservaComDinheiro(String idCliente, long idReserva, String nomeTitular) {
		return this.pagamentoController.pagarReservaComDinheiro(this.usuarioController, idCliente, this.reservasSessionController, idReserva, nomeTitular);
	}

	public String pagarReservaComCartao(String idCliente, long idReserva, String nomeTitular, String numCartao, String validade, String codigoDeSeguranca, int qtdeParcelas) {
		return this.pagamentoController.pagarReservaComCartao(this.usuarioController, idCliente, this.reservasSessionController, idReserva, nomeTitular, numCartao, validade, codigoDeSeguranca, qtdeParcelas);
	}

	public String pagarReservaComPix(String idCliente, long idReserva,  String nomeTitular, String cpf, String banco){
		return this.pagamentoController.pagarReservaComPix(this.usuarioController, idCliente, this.reservasSessionController, idReserva, nomeTitular, cpf, banco);
	}

	public String cancelarReserva(String idCliente, String idReserva) {
		return this.usuarioController.cancelarReserva(this.usuarioController, idCliente, this.reservasSessionController, idReserva);
	}

	public String disponibilizarAreaComum(String idAutenticacao, String tipoAreaComum, String titulo, LocalTime horarioInicio, LocalTime horarioFinal, double valorPessoa, boolean disponivel, int qtdMaxPessoas){
		return this.areaComumController.disponibilizarAreaComum(this.usuarioController, idAutenticacao, tipoAreaComum, titulo, horarioInicio, horarioFinal, valorPessoa, disponivel, qtdMaxPessoas);
	}

	public String alterarAreaComum(String idAutenticacao,long idAreaComum, LocalTime novoHorarioInicio, LocalTime novoHorarioFinal, double novoPreco, int capacidadeMax, boolean ativa){
		return this.areaComumController.alterarAreaComum(this.usuarioController, idAutenticacao, idAreaComum, novoHorarioInicio, novoHorarioFinal, novoPreco, capacidadeMax, ativa);
	}
	
 	public String exibirAreaComum(long idAreaComum){
		return this.areaComumController.exibirAreaComum(idAreaComum);
	}
 	
	public String[] listarAreasComuns() {
		return this.areaComumController.listarAreasComuns();
	}
	
	public String reservarAuditorio(String idAutenticacao, String idCliente, long idAuditorio, LocalDateTime dataInicio, LocalDateTime dataFim, int qtdPessoas) {
		return this.reservasSessionController.reservarAuditorio(this.usuarioController, idAutenticacao, idCliente, this.areaComumController, idAuditorio, dataInicio, dataFim, qtdPessoas);
	}
}