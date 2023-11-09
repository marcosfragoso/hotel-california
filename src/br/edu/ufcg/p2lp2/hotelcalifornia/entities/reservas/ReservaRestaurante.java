package br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas;

import br.edu.ufcg.p2lp2.hotelcalifornia.entities.refeicoes.Refeicao;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ReservaRestaurante extends Reserva {

	private Refeicao refeicao;
	
	public ReservaRestaurante(long id, Usuario cliente, LocalDateTime dataInicio, LocalDateTime dataFinal, int qtdPessoas, Refeicao refeicao) {
		super(id,cliente, dataInicio, dataFinal);
		if(!(cliente.getTipoUsuario().contains("CLI") || cliente.getTipoUsuario().contains("ADM"))) {
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO CADASTRAR UMA RESERVA");
		}
		LocalDateTime agora = LocalDateTime.now();
		if(dataInicio.isBefore(agora) || dataInicio.equals(agora)) {
			throw new HotelCaliforniaException("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA");
		}
		if(dataInicio.isAfter(agora) && agora.until(dataInicio, ChronoUnit.DAYS) < 1) {
			throw new HotelCaliforniaException("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA");
		}
		if(qtdPessoas > 50) {
			throw new HotelCaliforniaException("CAPACIDADE EXCEDIDA");
		}
		setQtdPessoas(qtdPessoas);
		this.refeicao = refeicao;
	}

	public Refeicao getRefeicao() {
		return this.refeicao;
	}
	
	public String getDataFinalFormatada() {
		return this.formataData(getDataFinal());
	}
	
	public String getDataInicioFormatada() {
		return this.formataData(getDataInicio());
	}
	
	@Override
	public String toString() {
		return getSituacaoCancelamento() + "[" + getId() + "] Reserva de RESTAURANTE em favor de:\n"
				+ "- " + getCliente().toString() + "\n"
				+ "Detalhes da reserva:\n" + "- Periodo: " + this.getDataInicioFormatada() 
				+ " " + this.refeicao.getHorarioInicio() + ":00 ate " + this.getDataFinalFormatada() 
				+ " " + this.refeicao.getHorarioFim() + ":00\n" + "- Qtde. de Convidados: " + getQtdPessoas() 
				+ " pessoa(s)\n" + "- Refeicao incluida: " + this.refeicao.toString() + "\n"
				+ "VALOR TOTAL DA RESERVA: R$" + this.formataValor(this.refeicao.getValor() * getQtdPessoas()) 
				+ " x" + (qtdeDiarias(getDataInicio(), getDataFinal()) + 1) + " (diarias) => R$" 
				+ this.formataValor(this.valorTotal()) + "\n" + "SITUACAO DO PAGAMENTO: " + getSituacaoPagamento() + ".";
	}

	@Override
	public double valorTotal(){
		return (this.refeicao.getValor() * getQtdPessoas() * (qtdeDiarias(getDataInicio(), getDataFinal()) + 1));
	}
}