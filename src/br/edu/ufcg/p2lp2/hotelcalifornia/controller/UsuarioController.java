package br.edu.ufcg.p2lp2.hotelcalifornia.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.Reserva;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Administrador;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Cliente;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Funcionario;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Gerente;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

public class UsuarioController {

	private static UsuarioController instance;
	private Map<String, Usuario> usuarios;
	private int quantidadeAdm;
	private int quantidadeGerente;
	private int quantidadeFuncionario;
	private int quantidadeCliente;
	private Administrador admPatente;
	private Gerente gerPatente;
	private Funcionario funPatente;
	private Cliente cliPatente;
	
	private UsuarioController() {
		this.usuarios = new HashMap<>();
		Usuario admDefault = new Usuario("ADM1", "Vini Malvadeza", "ADM", 666);
		this.admPatente = new Administrador();
		this.gerPatente = new Gerente();
		this.funPatente = new Funcionario();
		this.cliPatente = new Cliente();
		admDefault.addPatente(this.admPatente);
		this.quantidadeAdm =1;
		this.usuarios.put("ADM1", admDefault);
	}
	

	public static UsuarioController getInstance() {
		if (instance == null) {
			instance = new UsuarioController();
		}
		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}
	
	public void init() {
		this.usuarios.clear();
		Usuario admDefault = new Usuario("ADM1", "Vini Malvadeza", "ADM", 1234);
		admDefault.addPatente(this.admPatente);
		this.usuarios.put("ADM1", admDefault);
		this.quantidadeAdm = 1;
		this.quantidadeCliente = 0;
		this.quantidadeFuncionario = 0;
		this.quantidadeGerente = 0;
	}

	private String geraId(String tipoUsuario){
		if (tipoUsuario.equals("ADM")){
			return "ADM" + (this.quantidadeAdm += 1);
		}
		if (tipoUsuario.equals("GER")){
			return "GER" + (this.quantidadeGerente +=1);
		}
		if (tipoUsuario.equals("FUN")){
			return "FUN" + (this.quantidadeFuncionario +=1);
		}
		if (tipoUsuario.equals("CLI")){
			return "CLI" + (this.quantidadeCliente +=1);
		}
		return null;
	}

	private void existeGerente(){
		if (!retornaChaveDoGerenteAtual().equals("Sem gerente.")) {
			throw new HotelCaliforniaException("SO DEVE HAVER UM GERENTE NO HOTEL");
		}
	}

	public String cadastrarUsuario(String idAutenticacao, String nome, String tipoUsuario, long documento) {
		if(tipoUsuario.contains("GER")){
			existeGerente();
		}
		existeUsuario(idAutenticacao);
		if (usuarios.get(idAutenticacao).getPatente().permissaoCriaUsuario(tipoUsuario)){
			Usuario novoUsuario = null;
			String novoId = geraId(tipoUsuario);
			if (idAutenticacao.contains("ADM")){
				novoUsuario = this.admPatente.criaSubordinado(novoId, idAutenticacao, nome, documento, tipoUsuario);
			}
			else if (idAutenticacao.contains("GER")){
				novoUsuario = this.gerPatente.criaSubordinado(novoId, idAutenticacao, nome, documento, tipoUsuario);
			}
			else if (idAutenticacao.contains("FUN")){
				novoUsuario = this.funPatente.criaSubordinado(novoId, idAutenticacao, nome, documento, tipoUsuario);
			}
			else if (idAutenticacao.contains("CLI")){
				novoUsuario = this.cliPatente.criaSubordinado(novoId, idAutenticacao, nome, documento, tipoUsuario);
			}
			usuarios.put(novoUsuario.getIdUser(), novoUsuario);
			return "["+novoId+"]";
		}
		throw new HotelCaliforniaException("USUARIO NAO EXISTE");
	}
	
	private String retornaChaveDoGerenteAtual() {
		for (Map.Entry<String, Usuario> entry : this.usuarios.entrySet()) {
			if (entry.getValue().getTipoUsuario().equals("GER")) {
				return entry.getKey();
			}
		}
		return "Sem gerente.";
	}

	public String atualizarUsuario(String idAutenticacao, String idUsuario, String novoTipoUsuario) {
		existeUsuario(idAutenticacao);
		existeUsuario(idUsuario);
		String novoId = geraId(novoTipoUsuario);
		StringBuilder sb = new StringBuilder();
		if (usuarios.get(idAutenticacao).getPatente().permissaoAtualizaTipoUsuario()) {
			if (this.usuarios.get(idUsuario).getTipoUsuario().equals("GER")){
				if (!retornaChaveDoGerenteAtual().equals("Sem gerente.")){
					String idAntigoGerente = geraId("FUN");
					this.usuarios.get(retornaChaveDoGerenteAtual()).setTipoUsuario("FUN"); // Atualiza cargo do antigo gerente para func
					this.usuarios.get(retornaChaveDoGerenteAtual()).addPatente(this.funPatente);
					this.usuarios.put(idAntigoGerente, this.usuarios.get(retornaChaveDoGerenteAtual())); // Cria novo registro do tipo func em usuarios
					this.usuarios.get(idUsuario).setIdUser(novoId);
					this.usuarios.remove(retornaChaveDoGerenteAtual()); // Exclui o registro do antigo gerente
					sb.append("ID antigo gerente: ").append(idAntigoGerente).append("\n");
				}
			}
			else {
				this.usuarios.get(idUsuario).setTipoUsuario(novoTipoUsuario); // Atualiza cargo do usuario
				this.usuarios.get(idUsuario).addPatente(this.funPatente);
				this.usuarios.put(novoId, this.usuarios.get(idUsuario)); // Cria registro com o novo cargo
				this.usuarios.get(idUsuario).setIdUser(novoId);
				this.usuarios.remove(idUsuario); // Exclui registro antigo
				return sb + "Novo id: " + novoId;
			}
		}
		throw new HotelCaliforniaException("APENAS O ADMINISTRADOR PODE ATUALIZAR OS USUARIOS");
	}

	public String exibirUsuario(String idUsuario){
		return this.usuarios.get(idUsuario).toString();
	}
	
 	public String[] listarUsuarios(){
		String[] listaUsuarios = new String[this.usuarios.size()];
		int cont = 0;
		for (Map.Entry<String, Usuario> entry : this.usuarios.entrySet()) {
			listaUsuarios[cont] = exibirUsuario(entry.getKey());
			cont++;
		}
		return listaUsuarios;
	}
 	
 	public Usuario getUsuario(String id) {
 		return this.usuarios.get(id);
 	}
 	
 	public void existeUsuario(String id) {
 		if(!this.usuarios.containsKey(id)){
			throw new HotelCaliforniaException("USUARIO NAO EXISTE");
		}
 	}

	public String cancelarReserva(UsuarioController userController, String idCliente, ReservasSessionController reservasController, String idReserva) {
		Reserva reserva = reservasController.getReserva(Long.parseLong(idReserva));
		Usuario usuario = userController.getUsuario(idCliente);
		if (!(usuario.getTipoUsuario().contains("CLI"))) {
			throw new HotelCaliforniaException("USUARIO NAO E CLIENTE");
		}
		if (reserva.getCliente().equals(usuario)) {
			LocalDateTime dataAtual = LocalDateTime.now();
			long difDias = ChronoUnit.DAYS.between(dataAtual, reserva.getDataInicio());
			if (difDias >= 1) {
				reserva.setSituacaoCancelamento("[CANCELADA]");
				return reserva.toString();
			} else {
				throw new HotelCaliforniaException("CLIENTE NAO PODE CANCELAR A RESERVA");
			}
		} else {
			throw new HotelCaliforniaException("SOMENTE O PROPRIO CLIENTE PODERA CANCELAR A SUA RESERVA");
		}
	}
}