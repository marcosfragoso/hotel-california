package br.edu.ufcg.p2lp2.hotelcalifornia.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.areaComum.AreaComum;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.Quarto;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.refeicoes.Refeicao;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.Reserva;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.ReservaAuditorio;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.ReservaQuarto;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.ReservaRestaurante;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

public class ReservasSessionController {

	private static ReservasSessionController instance;
	private List<Reserva> reservas;
	
	private ReservasSessionController() {
		this.reservas = new ArrayList<>();
	}
	
	public static ReservasSessionController getInstance() {
		if (instance == null) {
			instance = new ReservasSessionController();
		}
		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}

	public void init() {
		this.reservas.clear();
	}

	public boolean quartoEstaDisponivel(Quarto q, LocalDateTime dataInicio, LocalDateTime dataFinal) {
		for (Reserva reserva : reservas) {
			if (reserva instanceof ReservaQuarto) {
				Quarto quarto = ((ReservaQuarto) reserva).getQuarto();
				if (quarto.equals(q) && dataInicio.isBefore(reserva.getDataFinal()) && dataFinal.isAfter(reserva.getDataInicio())) {
					return false;
				}
				if (quarto.equals(q) && dataFinal.isAfter(reserva.getDataInicio()) && dataFinal.isBefore(reserva.getDataFinal())) {
					return false;
				}
			}
		}
		return true;
	}

	private void verificaData(LocalDateTime dataInicio, LocalDateTime dataFim) {
		LocalDateTime agora = LocalDateTime.now();

		if(dataInicio.isBefore(agora) || dataInicio.equals(agora)) {
			throw new HotelCaliforniaException("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA");
		}
		if(dataInicio.isAfter(agora) && agora.until(dataInicio, ChronoUnit.DAYS) < 1) {
			throw new HotelCaliforniaException("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA");
		}
		if(dataInicio.isAfter(dataFim)) {
			throw new HotelCaliforniaException("DATA DE INICIO PRECISA SER POSTERIOR A DATA FINAL");
		}
		if(Duration.between(dataInicio, dataFim).toDays() < 1) {
			throw new HotelCaliforniaException("TEMPO DE RESERVA MINIMA DE 01 (UM) DIA");
		}
	}
	
	private void verificaQuarto(UsuarioController userController, QuartoController quartoController, String idAutenticacao, String idCliente, int idNumQuarto, LocalDateTime dataInicio, LocalDateTime dataFinal) {
		userController.getUsuario(idAutenticacao).getPatente().permissaoReservaQuarto();
		if(!(quartoController.existeQuarto(idNumQuarto))) {
			throw new HotelCaliforniaException("Quarto inexistente");
		}
		if(!(this.quartoEstaDisponivel(quartoController.getQuarto(idNumQuarto), dataInicio, dataFinal))) {
			throw new HotelCaliforniaException("JA EXISTE RESERVA PARA ESTA DATA");
		}
	}
	
	public String reservarQuartoSingle(UsuarioController userController, String idAutenticacao, String idCliente, QuartoController quartoController, int idNumQuarto, LocalDateTime dataInicio, LocalDateTime dataFim, RefeicaoController refeicaoController, String[] idRefeicoes){
		userController.existeUsuario(idCliente);
		this.verificaData(dataInicio, dataFim);
		this.verificaQuarto(userController, quartoController, idAutenticacao, idCliente, idNumQuarto, dataInicio, dataFim);
		long[] idRefeicoesLong = Arrays.stream(idRefeicoes).mapToLong(Long::parseLong).toArray();
		Refeicao[] refeicoes = refeicaoController.pegaRefeicoes(idRefeicoesLong);
		Quarto q = quartoController.getQuarto(idNumQuarto);
		q.setDisponivel(false);
		Usuario usuario = userController.getUsuario(idCliente);
		long indiceReserva = this.reservas.size();
		ReservaQuarto reserva = new ReservaQuarto(indiceReserva, usuario, dataInicio, dataFim, q, refeicoes);
		this.reservas.add(reserva);
		reserva.setId((long) this.reservas.indexOf(reserva) + 1);
		return reserva.toString();
	}

	public String reservarQuartoDouble(UsuarioController userController, String idAutenticacao, String idCliente, QuartoController quartoController, int idNumQuarto, LocalDateTime dataInicio, LocalDateTime dataFim, RefeicaoController refeicaoController, String[] idRefeicoes, String[] teste){
		userController.existeUsuario(idCliente);
		this.verificaData(dataInicio, dataFim);
		this.verificaQuarto(userController, quartoController, idAutenticacao, idCliente, idNumQuarto, dataInicio, dataFim);
		long[] idRefeicoesLong = Arrays.stream(idRefeicoes).mapToLong(Long::parseLong).toArray();
		Refeicao[] refeicoes = refeicaoController.pegaRefeicoes(idRefeicoesLong);
		Quarto q = quartoController.getQuarto(idNumQuarto);
		q.setDisponivel(false);
		Usuario usuario = userController.getUsuario(idCliente);
		long indiceReserva = this.reservas.size();
		ReservaQuarto reserva = new ReservaQuarto(indiceReserva, usuario, dataInicio, dataFim, q, refeicoes, teste);
		this.reservas.add(reserva);
		reserva.setId((long) this.reservas.indexOf(reserva) + 1);
		return reserva.toString();
	}


	public String reservarQuartoFamily(UsuarioController userController, String idAutenticacao, String idCliente, QuartoController quartoController, int idNumQuarto, LocalDateTime dataInicio, LocalDateTime dataFim, RefeicaoController refeicaoController, String[] idRefeicoes, String[] teste, int qntdPessoas){
		userController.existeUsuario(idCliente);
		this.verificaData(dataInicio, dataFim);
		this.verificaQuarto(userController, quartoController, idAutenticacao, idCliente, idNumQuarto, dataInicio, dataFim);
		long[] idRefeicoesLong = Arrays.stream(idRefeicoes).mapToLong(Long::parseLong).toArray();
		Refeicao[] refeicoes = refeicaoController.pegaRefeicoes(idRefeicoesLong);
		Quarto q = quartoController.getQuarto(idNumQuarto);
		if (qntdPessoas > q.getQntdPessoas()) {
			throw new HotelCaliforniaException("CAPACIDADE EXCEDIDA");
		}
		q.setDisponivel(false);
		Usuario usuario = userController.getUsuario(idCliente);
		long indiceReserva = this.reservas.size();
		ReservaQuarto reserva = new ReservaQuarto(indiceReserva, usuario, dataInicio, dataFim, q, refeicoes, teste, qntdPessoas);
		this.reservas.add(reserva);
		reserva.setId((long) this.reservas.indexOf(reserva) + 1);
		return reserva.toString();
	}

	public String exibirReserva(UsuarioController userController, String idAutenticacao, long idReserva) {
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoVisualizaReserva();
		if (this.reservas.size() < (int) idReserva - 1) {
			throw new HotelCaliforniaException("RESERVA NAO ENCONTRADA.");
		}
		return this.reservas.get((int) idReserva - 1).toString();
	}
	
	private boolean verificaReserva(LocalDateTime dataInicio, int qtdePessoas) {
		LocalDateTime agora = LocalDateTime.now();
		if(dataInicio.isBefore(agora) || dataInicio.equals(agora)) {
			throw new HotelCaliforniaException("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA");
		}
		if(dataInicio.isAfter(agora) && agora.until(dataInicio, ChronoUnit.DAYS) < 1) {
			throw new HotelCaliforniaException("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA");
		}
		if(qtdePessoas > 50) {
			throw new HotelCaliforniaException("CAPACIDADE EXCEDIDA");
		}
		return true;
	}
	
	private void verificaOcupacaoRestaurante(LocalDateTime dataInicio, LocalDateTime dataFim) {
		for (Reserva r : this.reservas) {
			if (r instanceof ReservaRestaurante) {
				LocalDateTime reservaInicio = r.getDataInicio();
				LocalDateTime reservaFim = r.getDataFinal();

				if (dataInicio.isBefore(reservaFim) && dataFim.isAfter(reservaInicio)) {
					throw new HotelCaliforniaException("JA EXISTE RESERVA PARA ESTA DATA");
				}
			}
		}
	}
	
	public String reservarRestaurante(UsuarioController userController, String idAutenticacao, String usuario, LocalDateTime dataInicio, LocalDateTime dataFim, int qtdePessoas, RefeicaoController refeicaoController, String alimento) {
		userController.existeUsuario(usuario);
		userController.getUsuario(idAutenticacao).getPatente().permissaoReservaRestaurante();
		Usuario cliente = userController.getUsuario(usuario);
		if(!refeicaoController.existeRefeicao(Long.parseLong(alimento))) {
			throw new HotelCaliforniaException("REFEICAO NAO EXISTE");
		}
		Refeicao refeicao = refeicaoController.getRefeicao(Long.parseLong(alimento));
		if(this.verificaReserva(dataInicio, qtdePessoas)) {
			this.verificaOcupacaoRestaurante(dataInicio, dataFim);
			ReservaRestaurante reserva = new ReservaRestaurante((this.reservas.size() + 1), cliente, dataInicio, dataFim, qtdePessoas, refeicao);
			this.reservas.add(reserva);
			return reserva.toString();
		}
		return "Não foi possível reservar";
	}

	private boolean estaAtiva(LocalDateTime dataFim) {
		LocalDateTime dataAtual = LocalDateTime.now();
		return dataAtual.isBefore(dataFim);
	}

	public String[] listarReservasAtivasDoCliente(UsuarioController userController, String idAutenticacao, String idCliente) {
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoVisualizaReserva();
		ArrayList<String> reservasDoCliente = new ArrayList<>();
		Usuario cliente = userController.getUsuario(idCliente);
		for (Reserva reserva : this.reservas) {
			if (reserva.getCliente().equals(cliente) && estaAtiva(reserva.getDataFinal())) {
				reservasDoCliente.add(reserva.toString() + "\n------------------");
			}
		}
		if (reservasDoCliente.isEmpty()) {
			throw new HotelCaliforniaException("RESERVA NAO ENCONTRADA");
		}

		String[] retorno = reservasDoCliente.toArray(new String[0]);
		retorno[0] = "Vizualizar Reservas de: " + cliente.toString() + "\n==========\n" + retorno[0];
		retorno[retorno.length - 1] = retorno[retorno.length - 1] + "\n==========";
		return retorno;
	}

	public String[] listarReservasAtivasDoClientePorTipo(UsuarioController userController, String idAutenticacao, String idCliente, String tipo) {
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoVisualizaReserva();

		ArrayList<String> reservasDoCliente = new ArrayList<>();
		Usuario cliente = userController.getUsuario(idCliente);
		for (Reserva reserva : this.reservas) {
			if (reserva.getCliente().equals(cliente) && estaAtiva(reserva.getDataFinal())) {
				if (tipo.equalsIgnoreCase("Quarto") && reserva instanceof ReservaQuarto) {
					reservasDoCliente.add(reserva.toString() + "\n------------------");
				} else if (tipo.equalsIgnoreCase("Restaurante") && reserva instanceof ReservaRestaurante) {
					reservasDoCliente.add(reserva.toString() + "\n------------------");
				} else if (tipo.equalsIgnoreCase("Auditorio") && reserva instanceof ReservaAuditorio) {
					reservasDoCliente.add(reserva.toString() + "\n------------------");
				}

			}
		}
		if (reservasDoCliente.isEmpty()) {
			throw new HotelCaliforniaException("RESERVA NAO ENCONTRADA");
		}

		String[] retorno = reservasDoCliente.toArray(new String[0]);
		retorno[0] = "Vizualizar Reservas de: " + cliente.toString() + "\n==========\n" + retorno[0];
		retorno[retorno.length - 1] = retorno[retorno.length - 1] + "\n==========";
		return retorno;
	}

	public String[] listarReservasAtivasPorTipo(UsuarioController userController, String idAutenticacao, String tipo) {
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoVisualizaReserva();
		ArrayList<String> reservasDoCliente = new ArrayList<>();
		for (Reserva reserva : this.reservas) {
			if (estaAtiva(reserva.getDataFinal())) {
				if (tipo.equalsIgnoreCase("Quarto") && reserva instanceof ReservaQuarto) {
					reservasDoCliente.add(reserva.toString()  + "\n------------------");
				} else if (tipo.equalsIgnoreCase("Restaurante") && reserva instanceof ReservaRestaurante) {
					reservasDoCliente.add(reserva.toString() + "\n------------------");
				} else if (tipo.equalsIgnoreCase("Auditorio") && reserva instanceof ReservaAuditorio) {
					reservasDoCliente.add(reserva.toString() + "\n------------------");
				}
			}
		}
		if (reservasDoCliente.isEmpty()) {
			throw new HotelCaliforniaException("RESERVA NAO ENCONTRADA");
		}

		String[] retorno = reservasDoCliente.toArray(new String[0]);
		retorno[0] = "Vizualizar Reservas de: " + tipo + "\n==========\n" + retorno[0];
		retorno[retorno.length - 1] = retorno[retorno.length - 1] + "\n==========";
		return retorno;
	}

	public String[] listarReservasAtivas(UsuarioController userController, String idAutenticacao) {
		userController.getUsuario(idAutenticacao).getPatente().permissaoVisualizaReserva();
		ArrayList<String> reservasDoCliente = new ArrayList<>();
		for (Reserva reserva : this.reservas) {
			if (estaAtiva(reserva.getDataFinal())) {
				reservasDoCliente.add(reserva.toString()  + "\n------------------");
			}
		}
		if (reservasDoCliente.size() < 1) {
			throw new HotelCaliforniaException("RESERVA NAO ENCONTRADA");
		}

		String[] retorno = reservasDoCliente.toArray(new String[0]);
		retorno[0] = "==========\n" + retorno[0];
		retorno[retorno.length - 1] = retorno[retorno.length - 1] + "\n==========";
		return retorno;
	}

	public String[] listarReservasTodas(UsuarioController userController, String idAutenticacao) {
		userController.getUsuario(idAutenticacao).getPatente().permissaoVisualizaReserva();
		ArrayList<String> reservasDoCliente = new ArrayList<>();
		for (Reserva reserva : this.reservas) {
			reservasDoCliente.add(reserva.toString()  + "\n------------------");
		}
		if (reservasDoCliente.isEmpty()) {
			throw new HotelCaliforniaException("RESERVA NAO ENCONTRADA");
		}

		String[] retorno = reservasDoCliente.toArray(new String[0]);
		retorno[0] = "==========\n" + retorno[0];
		retorno[retorno.length - 1] = retorno[retorno.length - 1] + "\n==========";
		return retorno;
	}

	public List<Reserva> getReservas() {
		return this.reservas;
	}
	
	public Reserva getReserva(long id) {
		return this.reservas.get(((int) id) - 1);
	}
	
	private boolean verificaReservaAuditorio(LocalDateTime dataInicio, AreaComum auditorio, int qtdePessoas, LocalDateTime dataFim) {
		LocalDateTime agora = LocalDateTime.now();
		if(dataInicio.isBefore(agora) || dataInicio.equals(agora)) {
			throw new HotelCaliforniaException("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA");
		}
		if(dataInicio.isAfter(agora) && agora.until(dataInicio, ChronoUnit.DAYS) < 1) {
			throw new HotelCaliforniaException("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA");
		}
		if(qtdePessoas > 150 && qtdePessoas > auditorio.getQuantidadePessoas()) {
			throw new HotelCaliforniaException("CAPACIDADE EXCEDIDA");
		}
		for (Reserva r : this.reservas) {
			if (r instanceof ReservaAuditorio) {
				LocalDateTime reservaInicio = r.getDataInicio();
				LocalDateTime reservaFim = r.getDataFinal();

				if(auditorio.getTitulo().equals(((ReservaAuditorio) r).getAuditorio().getTitulo())) {
					if (dataInicio.isBefore(reservaFim) && dataFim.isAfter(reservaInicio)) {
						throw new HotelCaliforniaException("JA EXISTE RESERVA PARA ESTA DATA");
					}	
				}
			}
		}
		return true;
	}
	
	public String reservarAuditorio(UsuarioController userController, String idAutenticacao, String idCliente, AreaComumController areaController, long idAuditorio, LocalDateTime dataInicio, LocalDateTime dataFim, int qtdPessoas) {
		userController.existeUsuario(idAutenticacao);
		userController.existeUsuario(idCliente);
		userController.getUsuario(idAutenticacao).getPatente().permissaoReservaAuditorio();
		Usuario cliente = userController.getUsuario(idCliente);
		if(!areaController.existeAuditorio(idAuditorio)) {
			throw new HotelCaliforniaException("AREA COMUM NAO EXISTE");
		}
		AreaComum area = areaController.getArea(idAuditorio);
		if(this.verificaReservaAuditorio(dataInicio, area, qtdPessoas, dataFim)) {
			Reserva reserva = new ReservaAuditorio(((long)this.reservas.size() + 1), cliente, dataInicio, dataFim, qtdPessoas, area);
			this.reservas.add(reserva);
			return reserva.toString();
		}
		return "Não foi possível reservar";
	}
}