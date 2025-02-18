package com.cristinamellado.vivero.modelo;


public class Sesion {

	private Perfil perfil;
	private String usuario;

	public Sesion(Perfil p) {
		this.perfil = p;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
}
