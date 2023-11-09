package br.edu.ufcg.p2lp2.hotelcalifornia.entities.refeicoes;

import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Refeicao {
	private long id;
    private String titulo;
    private String tipo;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private double valor;
    private boolean disponibilidade;
    
    public Refeicao(long id, String titulo, String tipo, LocalTime horaInicio, LocalTime horaFim, double valor, boolean disponibilidade) {
    	this.id = id;
    	this.titulo = titulo;
    	if(!(tipo.equals("CAFE_DA_MANHA") || tipo.equals("ALMOCO") || tipo.equals("JANTAR"))) {
    		throw new HotelCaliforniaException("Refeição Inválida!");
    	}
    	this.tipo = tipo;
    	if(!(this.horaFinalValida(horaInicio, horaFim))) {
    		throw new HotelCaliforniaException("HORARIO DE FIM DEVE SER POSTERIOR AO HORARIO DE INICIO");
    	}
    	this.horarioInicio = horaInicio;
    	this.horarioFim = horaFim;
    	this.valor = valor;
    	this.disponibilidade = disponibilidade;
    }

    public long getId() {
    	return this.id;
    }
    
	public String getHorarioInicio() {
		return this.formataHora(this.horarioInicio);
	}

	public void setHorarioInicio(LocalTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public String getHorarioFim() {
		return this.formataHora(this.horarioFim);
	}

	public void setHorarioFim(LocalTime horarioFim) {
		this.horarioFim = horarioFim;
	}

	public double getValor() {
		return this.valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public boolean ehDisponivel() {
		return this.disponibilidade;
	}

	public void setDisponibilidade(boolean disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public String getTipo() {
		return this.tipo;
	}
	
	private String disponibilidade() {
		if(this.disponibilidade) {
			return "VIGENTE";
		}
		return "INDISPONIVEL";
	}
	
	private String formataHora(LocalTime data) {
		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
		String horaFormatada = formatterHora.format(data);
		return horaFormatada;
	}
    
	public boolean horaFinalValida(LocalTime horaInicio, LocalTime horaFim) {
		return horaFim.isAfter(horaInicio);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Refeicao)) {
			return false;
		}
		Refeicao other = (Refeicao) obj;
		return id == other.id && Objects.equals(titulo, other.titulo);
	}
	
	@Override
    public String toString() {
		String tipo = "";
		if(this.tipo.equals("CAFE_DA_MANHA")) {
			tipo = "Cafe-da-manha";
		}
		if(this.tipo.equals("ALMOCO")) {
			tipo = "Almoco";
		}
		if(this.tipo.equals("JANTAR")) {
			tipo = "Jantar";
		}
		return "[" + this.id + "] " + tipo + ": " + this.titulo + " (" + this.formataHora(this.horarioInicio).replace(":", "h") 
				+ " as "+ this.formataHora(this.horarioFim).replace(":", "h") + "). Valor por pessoa: R$" 
				+ new DecimalFormat("#,###.00").format(this.valor) 
				+ ". " + this.disponibilidade() + ".";
	}
}