package br.edu.ufcg.p2lp2.hotelcalifornia.entities.reservas;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.areaComum.AreaComum;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.Usuario;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

public class ReservaAuditorio extends Reserva {

	private AreaComum auditorio;
	
	public ReservaAuditorio(long id, Usuario cliente, LocalDateTime dataInicio, LocalDateTime dataFinal, int qtdePessoas, AreaComum auditorio) {
		super(id, cliente, dataInicio, dataFinal);
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
		if(qtdePessoas > 150 && qtdePessoas > auditorio.getQuantidadePessoas()) {
			throw new HotelCaliforniaException("CAPACIDADE EXCEDIDA");
		}
		setQtdPessoas(qtdePessoas);
		this.auditorio = auditorio;
	}
	
	public AreaComum getAuditorio() {
		return this.auditorio;
	}
	
	public String getDataFinalFormatada() {
		return this.formataData(getDataFinal());
	}
	
	public String getDataInicioFormatada() {
		return this.formataData(getDataInicio());
	}
	
	private String valorTodo() {
		if(this.valorTotal() == 0) {
			setSituacaoPagamento("REALIZADO");
			return "Grátis x" + (this.qtdeDiarias(getDataInicio(), getDataFinal()) + 1) + " (diarias) => Grátis";
		}
		String out = "R$";
		out += this.formataValor(this.auditorio.getValorPessoa() * getQtdPessoas());
		out += " x" + (this.qtdeDiarias(getDataInicio(), getDataFinal()) + 1) + " (diarias) => R$";
		out += this.formataValor(this.valorTotal());
		return out;
	}
	
	@Override
	public String toString() {
		return getSituacaoCancelamento() + "[" + getId() + "] " + "Reserva de AUDITORIO em favor de:\n"
				+ "- " + getCliente().toString() + "\n"
				+ "Detalhes da reserva:\n"
				+ "- Periodo: " + this.getDataInicioFormatada() + " " + this.auditorio.getHorarioInicio() + ":00 ate " 
				+ this.getDataFinalFormatada() + " " + this.auditorio.getHorarioFim() + ":00\n"
				+ "- Qtde. de Convidados: " + this.getQtdPessoas() + " pessoa(s)\n"
				+ "- Valor por pessoa: " + this.auditorio.valor() + "\n"
				+ "VALOR TOTAL DA RESERVA: " + this.valorTodo() + "\n"
				+ "SITUACAO DO PAGAMENTO: " + getSituacaoPagamento() + "."; 
	}
	
	@Override
	public double valorTotal() {
		return (this.auditorio.getValorPessoa() * getQtdPessoas() * (this.qtdeDiarias(getDataInicio(), getDataFinal()) + 1));
	}

}
