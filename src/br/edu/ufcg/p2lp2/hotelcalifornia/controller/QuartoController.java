package br.edu.ufcg.p2lp2.hotelcalifornia.controller;

import java.util.HashMap;
import java.util.Map;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.Quarto;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.QuartoDouble;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.QuartoFamily;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.QuartoSingle;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

public class QuartoController {

	private static QuartoController instance;
	private Map<Integer, Quarto> quartos;


	private QuartoController() {
		this.quartos = new HashMap<>();
	}
	
	public static QuartoController getInstance() {
		if (instance == null) {
			instance = new QuartoController();
		}
		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}
	
	public void init() {
		this.quartos.clear();
	}
	
	public String disponibilizarQuartoSingle(UsuarioController userController, String idAutenticacao, int idQuartoNum, double precoPorPessoa, double precoBase) {
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoDisponibilizaQuarto();

		if (this.quartos.containsKey(idQuartoNum)) {
			throw new HotelCaliforniaException("QUARTO JA EXISTE");
		}
		
		QuartoSingle quarto = new QuartoSingle(idQuartoNum, precoBase, precoPorPessoa);
		this.quartos.put(idQuartoNum, quarto);
		return quarto.toString();
	}
	
	public String disponibilizarQuartoDouble(UsuarioController userController, String idAutenticacao, int idQuartoNum, double precoPorPessoa, double precoBase, String[] pedidos) {
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoDisponibilizaQuarto();

		if (this.quartos.containsKey(idQuartoNum)) {
			throw new HotelCaliforniaException("QUARTO JA EXISTE");
		}
		
		QuartoDouble quarto = new QuartoDouble(idQuartoNum, precoBase, precoPorPessoa, pedidos);
		this.quartos.put(idQuartoNum, quarto);
		return quarto.toString();
	}

	public String disponibilizarQuartoFamily(UsuarioController userController, String idAutenticacao, int idQuartoNum, double precoPorPessoa, double precoBase, String[] pedidos, int qntdMaxPessoas) {
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoDisponibilizaQuarto();

		if (this.quartos.containsKey(idQuartoNum)) {
			throw new HotelCaliforniaException("QUARTO JA EXISTE");
		}
		if (qntdMaxPessoas > 10) {
			return "Quarto excede o limite de capacidade";
		}
		QuartoFamily quarto = new QuartoFamily(idQuartoNum, precoBase, precoPorPessoa, pedidos, qntdMaxPessoas);
		this.quartos.put(idQuartoNum, quarto);
		return quarto.toString();
	}

	public String exibirQuarto(int idQuartoNum) {
		if (this.quartos.containsKey(idQuartoNum)) {
			return this.quartos.get(idQuartoNum).toString();
		} else {
			return "Quarto inexistente.";
		}
	}

	public String[] listarQuartos(){
		String[] q = new String[this.quartos.size()];
		int i = 0;
		for (Quarto quarto: this.quartos.values()) {
			q[i] = quarto.toString();
			i++;
		}
		return q;
	}
	
	public Quarto getQuarto(int id) {
		return this.quartos.get(id);
	}
	
	public boolean existeQuarto(int id) {
		return this.quartos.containsKey(id);
	}
}