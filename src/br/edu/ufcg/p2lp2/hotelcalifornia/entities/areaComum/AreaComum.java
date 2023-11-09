package br.edu.ufcg.p2lp2.hotelcalifornia.entities.areaComum;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class AreaComum {
    private long id;
    private String titulo;
    private String tipoAreaComum;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private double valorPessoa;
    private boolean disponibilidade;
    private int quantidadePessoas;

    public AreaComum(long id, String titulo, String tipoAreaComum, LocalTime horaInicio, LocalTime horaFim, double valorPessoa, boolean disponibilidade, int quantidadePessoas){
        this.id = id;
        this.titulo = titulo;
        this.tipoAreaComum = tipoAreaComum;
        this.horarioInicio = horaInicio;
        this.horarioFim = horaFim;
        this.valorPessoa = valorPessoa;
        this.disponibilidade = disponibilidade;
        this.quantidadePessoas = quantidadePessoas;
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

    public double getValorPessoa() {
        return this.valorPessoa;
    }

    public void setValorPessoa(double valorPessoa) {
        this.valorPessoa = valorPessoa;
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

    public String getTipoAreaComum() {
        return this.tipoAreaComum;
    }
    public void setTipoAreaComum(String tipoAreaComum) {
        this.tipoAreaComum = tipoAreaComum;
    }

    public int getQuantidadePessoas() {
        return this.quantidadePessoas;
    }

    public void setQuantidadePessoas(int quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
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

    public String valor() {
    	if(this.valorPessoa == 0) {
    		return "Grátis";
    	}
    	return "R$" + new DecimalFormat("#,###.00").format(this.valorPessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaComum areaComum = (AreaComum) o;
        return id == areaComum.id && Objects.equals(titulo, areaComum.titulo);
    }

    @Override
    public String toString() {
        return "[" + this.id + "] " + this.tipoAreaComum + ": " + this.titulo + " (" + this.formataHora(this.horarioInicio).replace(":", "h")
                + " as "+ this.formataHora(this.horarioFim).replace(":", "h") + "). Valor por pessoa: "
                + this.valor() + ". Capacidade: " + this.quantidadePessoas + " pessoa(s). "
                + this.disponibilidade() + ".";
    }
}
