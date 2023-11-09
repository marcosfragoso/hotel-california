package br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios;

import java.util.Objects;

public class Usuario {

	private String idUser;
	private String nome;
	private String tipoUsuario;
	private Patente patente;
	private long document;

	public Usuario(String idUser, String nome, String tipoUsuario, long document) {
		this.idUser = idUser;
		this.nome = nome;
		this.tipoUsuario = tipoUsuario;
		this.document = document;
	}
	
	public void addPatente(Patente p) {
		this.patente = p;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Patente getPatente() {
		return patente;
	}

	public long getDocument() {
		return document;
	}

	public void setDocument(long document) {
		this.document = document;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUser);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(idUser, other.idUser);
	}

	@Override
	public String toString(){
		return "[" + this.idUser + "] " + this.nome + " (No. Doc. " + this.document + ")";
	}
}
