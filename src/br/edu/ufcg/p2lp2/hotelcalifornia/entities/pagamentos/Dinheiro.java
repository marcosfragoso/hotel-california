package br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos;

import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.Reserva;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;

import java.text.DecimalFormat;

public class Dinheiro extends Pagamento{

    private String nomeTitular;

    public Dinheiro(Reserva reserva, Usuario usuario, FormaPagamento formaPagamento, double valor, String nomeTitular) {
        super(reserva, usuario, formaPagamento, valor);
        this.nomeTitular = nomeTitular;
    }

    private double calculaDesconto(){
        if (getFormaPagamento().getPercentualDesconto() > 1){
            return getValor() - ((getFormaPagamento().getPercentualDesconto() / 100) * getValor());
        } else {
            return getValor() - ((getFormaPagamento().getPercentualDesconto()) * getValor());
        }
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    @Override
    public String toString(){
    	DecimalFormat df = new DecimalFormat("#,###.00");
        return "Total Efetivamente Pago: R$" + df.format(calculaDesconto()) + " em 1x de R$" + df.format(calculaDesconto());
    }
}
