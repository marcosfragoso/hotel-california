package br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos;

import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.Reserva;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;
import java.text.DecimalFormat;

public class Pix extends Pagamento{

    private String nomeTitular;
    private String cpf;
    private String banco;

    public Pix(Reserva reserva, Usuario usuario, FormaPagamento formaPagamento, double valor, String nomeTitular, String cpf, String banco) {
        super(reserva, usuario, formaPagamento, valor);
        this.nomeTitular = nomeTitular;
        this.cpf = cpf;
        this.banco = banco;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    private double calculaDesconto(){
        if (getFormaPagamento().getPercentualDesconto() > 1){
            return getValor() - ((getFormaPagamento().getPercentualDesconto() / 100) * getValor());
        } else {
            return getValor() - ((getFormaPagamento().getPercentualDesconto()) * getValor());
        }
    }

    @Override
    public String toString(){
    	DecimalFormat df = new DecimalFormat("#,###.00");
        return "Total Efetivamente Pago: R$" + df.format(calculaDesconto()) + " em 1x de R$" + df.format(calculaDesconto());
    }
}
