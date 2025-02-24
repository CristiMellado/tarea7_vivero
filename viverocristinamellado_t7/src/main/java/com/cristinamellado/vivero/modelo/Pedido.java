package com.cristinamellado.vivero.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="pedidos")
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Date fecha;
	
	@OneToMany(cascade = CascadeType.ALL) 
	@JoinColumn(name="id_pedido")
	private List<Ejemplar> listaEjemplares = new LinkedList<Ejemplar>();
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;

	public Pedido() {}
	
	public Pedido(Date fecha, List<Ejemplar> listaEjemplares) {
		this.fecha = fecha;
		this.listaEjemplares = listaEjemplares;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Ejemplar> getListaEjemplares() {
		return listaEjemplares;
	}

	public void setListaEjemplares(List<Ejemplar> listaEjemplares) {
		this.listaEjemplares = listaEjemplares;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	
}
