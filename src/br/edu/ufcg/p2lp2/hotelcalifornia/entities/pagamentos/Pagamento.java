package br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos;

import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.Reserva;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;

public abstract class Pagamento {

    private Reserva reserva;
    private Usuario usuario;
    private FormaPagamento formaPagamento;
    private double valor;

    public Pagamento(Reserva reserva, Usuario usuario, FormaPagamento formaPagamento, double valor) {
        this.reserva = reserva;
        this.usuario = usuario;
        this.formaPagamento = formaPagamento;
        this.valor = valor;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public abstract String toString();
}
