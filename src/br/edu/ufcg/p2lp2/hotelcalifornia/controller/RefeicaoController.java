package br.edu.ufcg.p2lp2.hotelcalifornia.controller;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.refeicoes.Refeicao;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

public class RefeicaoController {

	private static RefeicaoController instance;
	private Map<Long, Refeicao> refeicoes;
	
	private RefeicaoController() {
		this.refeicoes = new HashMap<>();
	}
	
	public static RefeicaoController getInstance() {
		if (instance == null) {
			instance = new RefeicaoController();
		}
		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}
	
	public void init() {
		this.refeicoes.clear();
	}
	
	private boolean verificaHora(LocalTime horarioInicio, LocalTime horarioFinal) {
		return horarioFinal.isAfter(horarioInicio);
	}
	
	public String disponibilizarRefeicao(UsuarioController userController, String idAutenticacao, String tipoRefeicao, String titulo, LocalTime horarioInicio, LocalTime horarioFinal, double valor, boolean disponivel) {
		userController.existeUsuario(idAutenticacao);
		userController.getUsuario(idAutenticacao).getPatente().permissaoDisponibilizaRefeicao();
		if(!(this.verificaHora(horarioInicio, horarioFinal))) {
			throw new HotelCaliforniaException("HORARIO DE FIM DEVE SER POSTERIOR AO HORARIO DE INICIO");
    	}
		if(!(tipoRefeicao.equals("CAFE_DA_MANHA") || tipoRefeicao.equals("ALMOCO") || tipoRefeicao.equals("JANTAR"))) {
    		return "Refeição Inválida!";
    	}
		for(Refeicao r: this.refeicoes.values()) {
			if(r.getTitulo().equals(titulo)) {
				throw new HotelCaliforniaException("REFEICAO JA EXISTE");
			}
		}
		Refeicao refeicao = new Refeicao((this.refeicoes.size() + 1), titulo, tipoRefeicao, horarioInicio, horarioFinal, valor, disponivel);
		this.refeicoes.put((long)(this.refeicoes.size() + 1), refeicao);
		return refeicao.toString();
	}
	
	public String alterarRefeicao(long idRefeicao, LocalTime horarioInicio, LocalTime horarioFinal, boolean disponivel, double valor) {
		if(!(this.refeicoes.containsKey(idRefeicao))) {
			throw new HotelCaliforniaException("REFEICAO NAO EXISTE");
		}
		if(!(this.verificaHora(horarioInicio, horarioFinal))) {
			throw new HotelCaliforniaException("HORARIO DE FIM DEVE SER POSTERIOR AO HORARIO DE INICIO");
		}
		this.refeicoes.get(idRefeicao).setDisponibilidade(disponivel);
		this.refeicoes.get(idRefeicao).setHorarioFim(horarioFinal);
		this.refeicoes.get(idRefeicao).setHorarioInicio(horarioInicio);
		this.refeicoes.get(idRefeicao).setValor(valor);
		return this.refeicoes.get(idRefeicao).toString();	
	}
	
	public String exibirRefeicao(long idRefeicao) {
		if(!(this.refeicoes.containsKey(idRefeicao))) {
			return "Refeição não existe!";
		}
		return this.refeicoes.get(idRefeicao).toString();
	}

	public String[] listarRefeicoes() {
		String[] out = new String[this.refeicoes.size()];
		for(int i = 1; i <= this.refeicoes.size(); i ++) {
			out[i - 1] = (this.refeicoes.get((long)i)).toString();
		}
		return out;
	}
	
	public Refeicao[] pegaRefeicoes(long[] r) {
		Refeicao[] refeicoes = new Refeicao[r.length];
		int indice = 0;

		for (long chave : r) {
			refeicoes[indice] = this.refeicoes.get(chave);
			indice++;
		}

		return refeicoes;
	}
	
	public boolean existeRefeicao(long id) {
		return this.refeicoes.containsKey(id);
	}
	
	public Refeicao getRefeicao(long id) {
		return this.refeicoes.get(id);
	}
}