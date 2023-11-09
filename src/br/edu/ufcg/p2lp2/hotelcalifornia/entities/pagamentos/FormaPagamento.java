package br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos;

import java.text.DecimalFormat;
import java.util.Objects;

public class FormaPagamento {
    private String id;
    private TipoFormaPagamento formaPagamento;
    private double percentualDesconto;

    public FormaPagamento(String id, TipoFormaPagamento formaPagamento, double percentualDesconto) {
        this.id = id;
        this.formaPagamento = formaPagamento;
        this.percentualDesconto = percentualDesconto;
    }

    public String getId() {
        return id;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    public String getFormaPagamento() {
        return formaPagamento.toString();
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = TipoFormaPagamento.valueOf(formaPagamento);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void alterarDesconto(double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    };


    @Override
    public String toString(){
        String forma = "";
        if (this.formaPagamento.equals(TipoFormaPagamento.DINHEIRO)) {
            forma = "DINHEIRO";
        }
        if (this.formaPagamento.equals(TipoFormaPagamento.CARTAO)) {
            forma = "CARTAO DE CREDITO";
        }
        if (this.formaPagamento.equals(TipoFormaPagamento.PIX)) {
            forma = "PIX";
        }

        if (percentualDesconto >= 1) {
            String desconto = new DecimalFormat("0").format(percentualDesconto);
            return "["+ this.id + "] " + "Forma de pagamento: " + forma + " (" + desconto + "% de desconto em pagamentos)";
        }
        double teste = percentualDesconto;

        String desconto = new DecimalFormat("0").format(teste*100);
        return "["+ this.id + "] " + "Forma de pagamento: " + forma + " (" + desconto + "% de desconto em pagamentos)";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormaPagamento that = (FormaPagamento) o;
        return Objects.equals(id, that.id) && formaPagamento == that.formaPagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, formaPagamento);
    }
}
