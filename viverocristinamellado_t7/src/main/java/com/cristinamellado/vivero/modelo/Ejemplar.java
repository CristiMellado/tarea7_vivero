package com.cristinamellado.vivero.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="ejemplares")
public class Ejemplar implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column
	private String nombre;
	
	//relación de las tablas
	@ManyToOne
	@JoinColumn(name="id_planta")
	private Planta planta;
	
	//relacion un ejemplar tiene muchos mensajes
	@OneToMany(cascade = CascadeType.ALL) 
	@JoinColumn(name="id_ejemplar")
	private List<Mensaje> listaMensajes = new LinkedList<Mensaje>();
	
	@Column
	private boolean disponible;
	
	@ManyToOne
	@JoinColumn(name="id_pedido")
	private Pedido pedido;

	public Ejemplar() {}
	
	public Ejemplar(String nombre, Planta planta, List<Mensaje> listaMensajes) {
		this.nombre = nombre;
		this.planta = planta;
		this.listaMensajes = listaMensajes;
		this.disponible = true;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreString() {
		return nombre;
	}

	public void setNombreString(String nombreString) {
		this.nombre = nombreString;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Planta getPlanta() {
		return planta;
	}

	public void setPlanta(Planta planta) {
		this.planta = planta;
	}


	public List<Mensaje> getListaMensajes() {
		return listaMensajes;
	}


	public void setListaMensajes(List<Mensaje> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}
	

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String datosVersionCorta() {
		String resultado = "";
		resultado += "IdEjemplar: " + id + " | " + "Nombre: " + nombre;
		return resultado;
	}


}
