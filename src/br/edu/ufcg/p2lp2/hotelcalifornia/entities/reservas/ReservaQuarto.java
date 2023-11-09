package br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas;

import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.QuartoDouble;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.QuartoFamily;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.refeicoes.Refeicao;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.Quarto;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ReservaQuarto extends Reserva{
    private Quarto quarto;
    private Refeicao[] refeicoes;
    private String[] pedidos;

    public ReservaQuarto(long id, Usuario cliente, LocalDateTime dataInicio, LocalDateTime dataFinal, Quarto quarto, Refeicao[] refeicoes) {
        super(id, cliente, dataInicio, dataFinal);
        this.quarto = quarto;
        this.refeicoes = refeicoes;
        if (quarto instanceof QuartoDouble) {
            setQtdPessoas(2);
        }
    }

    public ReservaQuarto(long id, Usuario cliente, LocalDateTime dataInicio, LocalDateTime dataFinal, Quarto quarto, Refeicao[] refeicoes, String[] teste) {
        super(id, cliente, dataInicio, dataFinal);
        this.quarto = quarto;
        this.refeicoes = refeicoes;
        if (quarto instanceof QuartoDouble) {
            setQtdPessoas(2);
        }
        this.pedidos = teste;
    }


    public ReservaQuarto(long id, Usuario cliente, LocalDateTime dataInicio, LocalDateTime dataFinal, Quarto quarto, Refeicao[] refeicoes, String[] teste, int qntdPessoas) {
        super(id, cliente, dataInicio, dataFinal);
        this.quarto = quarto;
        this.refeicoes = refeicoes;
        if (quarto instanceof QuartoFamily) {
            setQtdPessoas(qntdPessoas);
        }
        this.pedidos = teste;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    @Override
    public String toString() {
        String qntdPessoasFormatado = String.format("%02d", this.getQtdPessoas());
        double diaria = this.quarto.getPrecoBase() + this.quarto.getPrecoPorPessoa() * this.quarto.getQntdPessoas() + valorRefeicao();
        
        return getSituacaoCancelamento() + "[" + this.getId() + "] Reserva de quarto em favor de:\n- " 
        		+ this.getCliente().toString() + "\nDetalhes da instalacao:\n- " + this.quarto.toString() 
        		+ "\nDetalhes da reserva:\n" + "- Periodo: " + this.formataData(getDataInicio()) + " 14:00:00 ate " 
        		+ this.formataData(getDataFinal()) + " 12:00:00" + "\n- No. Hospedes: " + qntdPessoasFormatado + " pessoa(s)" 
        		+  "\n- Refeicoes incluidas: " + Arrays.toString(refeicoes()) + "\n- Pedidos: " + Arrays.toString(pedidos) 
        		+ "\nVALOR TOTAL DA RESERVA: R$" + this.formataValor(diaria) + " x" + this.qtdeDiarias(getDataInicio(), getDataFinal())
        		+ " (diarias) => R$" + this.formataValor(valorTotal()) + "\nSITUACAO DO PAGAMENTO: " + this.getSituacaoPagamento() + ".";
    }

    
    private String[] refeicoes() {
    	String[] ref = new String[refeicoes.length];
        for (int i = 0; i < refeicoes.length; i++) {
            if (refeicoes[i] != null) {
                ref[i] = refeicoes[i].toString();
            } else {
                ref[i] = "null";
            }
        }
        return ref;
    }
    
    private double valorRefeicao() {
    	double valorRefeicao = 0;
        for (int i = 0; i < refeicoes.length; i++) {
            if (refeicoes[i] != null) {
                valorRefeicao += refeicoes[i].getValor();
            }
        }
        valorRefeicao = valorRefeicao * this.getQtdPessoas();
        return valorRefeicao;
    }
    
    @Override
    public double valorTotal(){
        return (this.quarto.getPrecoBase() + this.quarto.getPrecoPorPessoa() * this.quarto.getQntdPessoas() + valorRefeicao()) * this.qtdeDiarias(getDataInicio(), getDataFinal());
    }
}