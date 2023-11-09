package br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.pagamentos.Pagamento;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;

public abstract class Reserva {

	private long id;
	private Usuario cliente;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;
	private int qtdPessoas;
	private String situacaoPagamento;
	private Pagamento pagamento;
	private String situacaoCancelamento;
	
	public Reserva(long id, Usuario cliente, LocalDateTime dataInicio, LocalDateTime dataFinal) {
		this.id = id;
		this.cliente = cliente;
		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
		this.qtdPessoas = 1;
		this.situacaoPagamento = "PENDENTE";
		this.pagamento = null;
		this.situacaoCancelamento = "";
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public abstract double valorTotal();

	public String getSituacaoPagamento() {
		return situacaoPagamento;
	}

	public void setSituacaoPagamento(String situacaoPagamento) {
		this.situacaoPagamento = situacaoPagamento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public LocalDateTime getDataInicio() {
		return this.dataInicio;
	}

	public LocalDateTime getDataFinal() {
		return this.dataFinal;
	}

	public int getQtdPessoas() {
		return qtdPessoas;
	}
	
	public void setQtdPessoas(int qtd) {
		this.qtdPessoas = qtd;
	}

	public String getSituacaoCancelamento() {
		return this.situacaoCancelamento;
	}
	
	public void setSituacaoCancelamento(String situacaoCancelamento) {
		this.situacaoCancelamento = situacaoCancelamento;
	}
	
	protected int qtdeDiarias(LocalDateTime data, LocalDateTime data2) {
		LocalDateTime dataInicio = LocalDateTime.of(data.getYear(), data.getMonth(), data.getDayOfMonth(), 00, 00);
		LocalDateTime dataFinal = LocalDateTime.of(data2.getYear(), data2.getMonth(), data2.getDayOfMonth(), 00, 00);
		int diarias = (int)Duration.between(dataInicio, dataFinal).toDays();
		return  diarias;
	}
	
	protected String formataData(LocalDateTime data) {
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		return formatterData.format(data);
	}
	
	protected String formataValor(double valor) {
		return new DecimalFormat("#,###.00").format(valor);
	}
}