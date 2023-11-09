package br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios;

public interface Patente {

	Usuario criaSubordinado(String idUser, String id, String nome, long documento, String cargo);
	boolean permissaoCriaUsuario(String cargo);
	boolean permissaoDisponibilizaFormaDePagamento();
	boolean permissaoAlteraFormaPagamento();
	boolean permissaoAtualizaTipoUsuario();
	boolean permissaoDisponibilizaAreaComum();
	boolean permissaoReservaQuarto();
	boolean permissaoDisponibilizaQuarto();
	boolean permissaoDisponibilizaRefeicao();
	boolean permissaoReservaRestaurante();
	boolean permissaoVisualizaReserva();
	boolean permissaoReservaAuditorio();

}
