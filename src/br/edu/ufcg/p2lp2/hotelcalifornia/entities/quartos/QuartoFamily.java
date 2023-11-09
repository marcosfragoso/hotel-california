package br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos;

import java.text.DecimalFormat;
import java.util.Arrays;

public class QuartoFamily extends QuartoDouble {
	
    private String[] pedidos;
    
    public QuartoFamily(int id, double precoBase, double precoPorPessoa, String[] pedidos, int qntdPessoas) {
        super(id, precoBase, precoPorPessoa, pedidos);
        this.pedidos = pedidos;
        setQntdPessoas(qntdPessoas);
    }

    @Override
    public String toString() {
    	DecimalFormat df = new DecimalFormat("#,###.00");
        String precoBase = df.format(getPrecoBase());
        String precoPessoa = df.format(getPrecoPorPessoa());
        String diaria = df.format(getValorDiaria());
        String capacidade = String.format("%02d", getQntdPessoas());

        return "[" + getId() + "] Quarto Family (custo basico: R$" + precoBase +
                "; por pessoa: R$" + precoPessoa + " >>> R$" + diaria + " diária). Capacidade: " + capacidade + " pessoa(s). Pedidos: " + Arrays.toString(pedidos);
    }

}
