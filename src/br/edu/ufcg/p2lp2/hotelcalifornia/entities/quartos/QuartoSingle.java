package br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos;

import java.text.DecimalFormat;

public class QuartoSingle extends Quarto {

    public QuartoSingle(int numeroQuarto, double precoBase, double precoPorPessoa) {
        super(numeroQuarto, precoBase, precoPorPessoa);
        setQntdPessoas(1);
    }

    @Override
    public String toString() {
    	DecimalFormat df = new DecimalFormat("#,###.00");
        String precoBase = df.format(getPrecoBase());
        String precoPessoa = df.format(getPrecoPorPessoa());
        String diaria = df.format(getValorDiaria());

        return "[" + getId() + "] Quarto Single (custo basico: R$" + precoBase +
                "; por pessoa: R$" + precoPessoa + " >>> R$" + diaria + " diária)";
    }
}
