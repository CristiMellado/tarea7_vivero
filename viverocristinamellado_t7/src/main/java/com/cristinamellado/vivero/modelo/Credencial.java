package com.cristinamellado.vivero.modelo;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="credenciales")
public class Credencial implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String usuario;
	
	@Column
	@Enumerated(EnumType.STRING) //va a tomar el valor del ENUM
	private Perfil perfil;
	

	
	public Credencial() {}
	
	public Credencial(String usuario, Perfil perfil) {
		this.usuario = usuario;
		this.perfil = perfil;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String datosVersionCorta() {
		String resultado = "";
		resultado += "IdCredencial: " + id + " | " + "Usuario " + usuario;
		return resultado;
	}
	
	
}
