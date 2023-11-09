package br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos;

import br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas.Reserva;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import java.text.DecimalFormat;

public class Cartao extends Pagamento {
    private String nomeTitular;
    private String numero;
    private String validade;
    private String digitoVerificador;
    private int quantidadeParcelas;

    public Cartao(Reserva reserva, Usuario usuario, FormaPagamento formaPagamento, String nomeTitular, double valor, String numero, String validade, String digitoVerificador, int quantidadeParcelas) {
        super(reserva, usuario, formaPagamento, valor);
        if (quantidadeParcelas <= 12 && quantidadeParcelas >= 1 && numero.length() == 16) {
            this.nomeTitular = nomeTitular;
            this.numero = numero;
            this.validade = validade;
            this.digitoVerificador = digitoVerificador;
            this.quantidadeParcelas = quantidadeParcelas;
        } else {
            throw new HotelCaliforniaException("Quantidade de parcelas não permitida.");
        }
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    private double calculaDesconto(){
        if (getFormaPagamento().getPercentualDesconto() > 1){
            return getValor() - ((getFormaPagamento().getPercentualDesconto() / 100) * getValor());
        } else {
            return getValor() - ((getFormaPagamento().getPercentualDesconto()) * getValor());
        }
    }

    @Override
    public String toString() {
        double valorTotal = getValor();
        double valorParcela = calculaDesconto() / quantidadeParcelas;

        DecimalFormat df = new DecimalFormat("#,###.00");
        DecimalFormat df2 = new DecimalFormat("0.0000");
        String[] converte = df2.format(valorParcela).split(",");

        if (converte[1].charAt(2) == '4' &&  converte[1].charAt(3) == '4' || converte[1].charAt(2) == '3' && converte[1].charAt(3) == '3' || converte[1].charAt(2) == '2' && converte[1].charAt(3) == '2' || converte[1].charAt(2) == '1' && converte[1].charAt(3) == '1') {
            valorParcela = valorParcela + 0.01;
        }

        return "Total Efetivamente Pago: R$" + df.format(valorTotal) +
                " em " + quantidadeParcelas + "x de R$" + df.format(valorParcela);
    }

}
