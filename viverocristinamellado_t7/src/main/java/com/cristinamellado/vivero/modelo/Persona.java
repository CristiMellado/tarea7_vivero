package com.cristinamellado.vivero.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="personas")
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column
	private String nombre;
	
	@Column(unique = true)
	private String email;
	
	//Relacion 1 n de mensajes 
	@OneToMany
	@JoinColumn(name="id_persona")
	private List<Mensaje> listaMensajes= new LinkedList<Mensaje>();

	public Persona() {}
		
	public Persona(String nombre, String email) {
		this.nombre = nombre;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public List<Mensaje> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<Mensaje> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	
	public String datosVersionCorta() {
		String resultado = "";
		resultado += "IdPersona: " + id + " | " + "Nombre: " + nombre;
		return resultado;
	}

	public String datosVersionLarga() {
		String resultado = "";
		resultado += "IdPersona: " + id + " | " + "Nombre: " + nombre + " | " + "E-mail: " + email;
		return resultado;
	}
	
	
}
