package br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios;

import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Gerente implements Patente {
	
	private Set<String> subordinados;
	private Set<String> permissoes;
	
	public Gerente() {
		this.subordinados = new HashSet<>(Arrays.asList("FUN", "CLI"));
		this.permissoes = new HashSet<>(Arrays.asList("RESERVA_QUARTO", "DISPONIBILIZA_REFEICAO", "RESERVA_RESTAURANTE", "VISUALIZA_RESERVA", "RESERVA_AUDITORIO"));
	}

	@Override
	public Usuario criaSubordinado(String idUser, String id, String nome, long documento, String cargo) {
		if (permissaoCriaUsuario(cargo)) {
			Usuario u = new Usuario(idUser, nome, cargo, documento);
			if ("FUN".equals(cargo)) {
				u.addPatente(new Funcionario());
			} else {
				u.addPatente(new Cliente());
			}
			return u;
		}
		return null;
	}

	@Override
	public boolean permissaoCriaUsuario(String cargo) {
		if (this.subordinados.contains(cargo)) {
			return true;
		}else {
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE CADASTRAR UM NOVO USUARIO DO TIPO " + cargo);
		}
	}

	@Override
	public boolean permissaoDisponibilizaFormaDePagamento(){
		if (this.permissoes.contains("DISPONIBILIZA_FORMA_PAGAMENTO")){
			return true;
		} else{
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE CADASTRAR UMA FORMA DE PAGAMENTO");
		}
	}

	@Override
	public boolean permissaoAtualizaTipoUsuario(){
		if (this.permissoes.contains("ATUALIZA_USUARIO")){
			return true;
		} else{
			throw new HotelCaliforniaException("APENAS O ADMINISTRADOR PODE ATUALIZAR OS USUARIOS");
		}
	}
	@Override
	public boolean permissaoDisponibilizaAreaComum(){
		if (this.permissoes.contains("DISPONIBILIZA_AREA_COMUM")){
			return true;
		} else{
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE CADASTRAR UMA AREA COMUM");
		}
	}
	@Override
	public boolean permissaoReservaQuarto(){
		if (this.permissoes.contains("RESERVA_QUARTO")){
			return true;
		} else{
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE RESERVAR UM QUARTO");
		}
	}

	@Override
	public boolean permissaoDisponibilizaRefeicao(){
		if (this.permissoes.contains("DISPONIBILIZA_REFEICAO")){
			return true;
		} else{
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE DISPONIBILIZAR UMA REFEICAO");
		}
	}

	@Override
	public boolean permissaoReservaRestaurante(){
		if (this.permissoes.contains("RESERVA_RESTAURANTE")){
			return true;
		} else{
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE RESERVAR UM RESTAURANTE");
		}
	}

	@Override
	public boolean permissaoVisualizaReserva(){
		if (this.permissoes.contains("VISUALIZA_RESERVA")){
			return true;
		} else{
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE VISUALIZAR UMA RESERVA");
		}
	}

	@Override
	public boolean permissaoReservaAuditorio(){
		if (this.permissoes.contains("RESERVA_AUDITORIO")){
			return true;
		} else{
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE RESERVAR UM AUDITORIO");
		}
	}


	@Override
	public boolean permissaoAlteraFormaPagamento(){
		if (this.permissoes.contains("ALTERA_FORMA_PAGAMENTO")){
			return true;
		} else{
			throw new HotelCaliforniaException("NAO E POSSIVEL PARA USUARIO GERENTE ALTERAR UMA FORMA DE PAGAMENTO");
		}
	}

	@Override public boolean permissaoDisponibilizaQuarto() {
		if (this.permissoes.contains("DISPONIBILIZA_QUARTO")){
			return true;
		} else {
			throw new HotelCaliforniaException("USUARIO NAO E ADMINISTRADOR");
		}
	}
}
