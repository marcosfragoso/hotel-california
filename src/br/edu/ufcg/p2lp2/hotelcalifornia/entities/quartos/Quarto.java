package br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos;

public abstract class Quarto {
    private int id;
    private double precoBase;
    private double precoPorPessoa;
    private int qntdPessoas;
    private boolean disponivel;

    public Quarto(int numeroQuarto, double precoBase, double precoPorPessoa) {
        this.id = numeroQuarto;
        this.precoBase = precoBase;
        this.precoPorPessoa = precoPorPessoa;
        this.qntdPessoas = 1;
        this.disponivel = true;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean taDisponivel) {
        this.disponivel = taDisponivel;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public int getId(){
        return id;
    }
    public double getPrecoPorPessoa() {
        return precoPorPessoa;
    }

    public int getQntdPessoas() {
        return qntdPessoas;
    }

    public void setQntdPessoas(int qntdPessoas) {
        this.qntdPessoas = qntdPessoas;
    }

    public double getValorDiaria() {
        double valor = precoBase + (precoPorPessoa * qntdPessoas);
        return Math.round(valor * 100.0) / 100.0;
    }
}

