package com.cristinamellado.vivero.modelo;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="mensajes")
public class Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP) 
	private Date fechahora;
	
	@Column
	private String mensaje;
	
	//relación mensajes-persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;
	
	//relación mensajes-ejemplar
	@ManyToOne
	@JoinColumn(name="id_ejemplar")
	private Ejemplar ejemplar;
	
	public Mensaje() {}

	public Mensaje(String mensaje, Persona persona,Ejemplar ejemplar) {
		this.fechahora = new Date(); 
		this.mensaje = mensaje;
		this.persona = persona;
		this.ejemplar = ejemplar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechahora() {
		return fechahora;
	}

	public void setFechahora(Date fechahora) {
		this.fechahora = fechahora;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

	public String datosVersionCorta() {
		String resultado = "";
		resultado += "IdMsj: " + id + " | " + "Fecha-Hora: " + fechahora + " | " + "Mensaje: " + mensaje;
		return resultado;
	}

	public String datosVersionLarga() {
		String resultado = "";
		resultado += "IdMsj: " + id + " | " + "Fecha-Hora: " + fechahora + " | " + "Mensaje: " + mensaje +
				" | " + "Nombre Ejemplar: " + ejemplar.getNombre() + " | " + "Nombre Persona: " + persona.getNombre();
		return resultado;
	}
	
	
	
}
