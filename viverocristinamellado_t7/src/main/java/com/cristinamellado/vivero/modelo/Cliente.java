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
@Table(name="clientes")
public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nombre;
	
	@Column
	private Date fechaNac;
	
	@Column(unique = true)
	private String nifnie;
	
	@Column
	private String direccion;
	
	@Column
	private String email;
	
	@Column
	private String telefono;
	
	@OneToMany(cascade = CascadeType.ALL) 
	@JoinColumn(name="id_cliente")
	private List<Pedido> listaPedidos = new LinkedList<Pedido>();
	
	public Cliente() {}
	
	public Cliente(String nombre, Date fechaNac, String nifnie, String direccion, String email, String telefono) {
		super();
		this.nombre = nombre;
		this.fechaNac = fechaNac;
		this.nifnie = nifnie;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
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

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getNifnie() {
		return nifnie;
	}

	public void setNifnie(String nifnie) {
		this.nifnie = nifnie;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	

	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", fechaNac=" + fechaNac + ", nifnie=" + nifnie
				+ ", direccion=" + direccion + ", email=" + email + ", telefono=" + telefono + "]";
	}
	

}
