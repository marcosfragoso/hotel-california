package br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos;

import java.text.DecimalFormat;
import java.util.Arrays;

public class QuartoDouble extends Quarto {

	private String[] pedidos;

    public QuartoDouble(int numeroQuarto, double precoBase, double precoPorPessoa, String[] pedidos) {
        super(numeroQuarto, precoBase, precoPorPessoa);
        this.pedidos = pedidos;
        setQntdPessoas(2);
    }

    @Override
    public String toString() {
    	DecimalFormat df = new DecimalFormat("#,###.00");
        String precoBase = df.format(getPrecoBase());
        String precoPessoa = df.format(getPrecoPorPessoa());
        String diaria = df.format(getValorDiaria());

        return "[" + getId() + "] Quarto Double (custo basico: R$" + precoBase +
                "; por pessoa: R$" + precoPessoa + " >>> R$" + diaria + " diária). Pedidos: " + Arrays.toString(pedidos);
    }
}
