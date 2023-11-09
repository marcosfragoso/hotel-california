package br.edu.ufcg.p2lp2.hotelcalifornia.controller;

import br.edu.ufcg.p2lp2.hotelcalifornia.entities.areaComum.AreaComum;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.areaComum.Auditorio;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AreaComumController {

	private static AreaComumController instance;
    private Map<Long, AreaComum> areasComuns;
    
    private AreaComumController() {
        this.areasComuns = new HashMap<>();
    }
    
    public static AreaComumController getInstance() {
		if (instance == null) {
			instance = new AreaComumController();
		}
		return instance;
	}

	public static void resetInstance() {
		instance = null;
	}
	
	public void init() {
		this.areasComuns.clear();
	}

    private void areaExiste(String titulo) {
        for (AreaComum areaComum : areasComuns.values()) {
            if (areaComum.getTitulo().equals(titulo)) {
                throw new HotelCaliforniaException("AREA COMUM JA EXISTE");
            }
        }
    }

    public String disponibilizarAreaComum(UsuarioController userController, String idAutenticacao, String tipoAreaComum, String titulo, LocalTime horarioInicio, LocalTime horarioFinal, double valorPessoa, boolean disponivel, int qtdMaxPessoas){
        areaExiste(titulo);
        userController.existeUsuario(idAutenticacao);
        userController.getUsuario(idAutenticacao).getPatente().permissaoDisponibilizaAreaComum();
    	if (horarioInicio.isAfter(horarioFinal)) {
            throw new HotelCaliforniaException("HORARIO DE FIM DEVE SER POSTERIOR AO HORARIO DE INICIO");
        }
        long id = areasComuns.size() + 1;
        AreaComum area;
        if (tipoAreaComum.equalsIgnoreCase("AUDITORIO")){
            area = new Auditorio(id, titulo, tipoAreaComum, horarioInicio, horarioFinal, valorPessoa, disponivel, qtdMaxPessoas);
            areasComuns.put(id, area);
        }
        else {
            throw new HotelCaliforniaException("AREA COMUM INDISPONIVEL");
        }
        return area.toString();
    }

    public String alterarAreaComum(UsuarioController userController, String idAutenticacao, long idAreaComum, LocalTime novoHorarioInicio, LocalTime novoHorarioFinal, double novoPreco, int capacidadeMax, boolean ativa) {
		if (!(userController.getUsuario(idAutenticacao).getTipoUsuario().contains("ADM"))) {
			throw new HotelCaliforniaException("USUARIO NAO E ADMINISTRADOR");
		}
    	if (novoHorarioInicio.isAfter(novoHorarioFinal)) {
            throw new HotelCaliforniaException("Horário final anterior ao horário inicial!");
        }
        if (areasComuns.containsKey(idAreaComum)) {
           AreaComum area = areasComuns.get(idAreaComum);
           area.setHorarioInicio(novoHorarioInicio);
           area.setHorarioFim(novoHorarioFinal);
           area.setValorPessoa(novoPreco);
           area.setQuantidadePessoas(capacidadeMax);
           area.setDisponibilidade(ativa);
           return area.toString();
        }
        else {
            throw new HotelCaliforniaException("AREA COMUM INDISPONIVEL");
        }
    }

    public String exibirAreaComum(long idAreaComum){
        if (areasComuns.containsKey(idAreaComum)) {
            AreaComum area = areasComuns.get(idAreaComum);
            return area.toString();
        } else {
            throw new HotelCaliforniaException("AREA COMUM INDISPONIVEL");
        }
    }

    public String[] listarAreasComuns(){
        String[] out = new String[this.areasComuns.size()];
        for(int i = 1; i <= this.areasComuns.size(); i ++) {
            out[i - 1] = (this.areasComuns.get((long)i)).toString();
        }
        return out;
    }
    
    public AreaComum getArea(long id) {
    	return this.areasComuns.get(id);
    }
    
    public boolean existeAuditorio(long id) {
    	return this.areasComuns.containsKey(id);
    }
}
