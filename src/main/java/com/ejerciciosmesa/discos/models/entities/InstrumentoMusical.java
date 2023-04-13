package com.ejerciciosmesa.discos.models.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.validation.constraints.NotBlank;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.TemporalType;
import java.util.Date;



@Entity
@Table(name="instrumentomusical")
public class InstrumentoMusical implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Column(name="nombre")
private String nombre;


@Column(name="tipo")
private String tipo;


@DateTimeFormat(pattern = "yyyy-MM-dd")
@Temporal(TemporalType.DATE)
@Column(name="fechacompra")
private Date fechaCompra;


@Column(name="precio")
private Double precio;


@Column(name="foto")
private String foto;



	
	public InstrumentoMusical() {}


	public Long getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getTipo() {
		return tipo;
}
public void setTipo(String tipo) {
	this.tipo = tipo;
}
public Date getFechaCompra() {
		return fechaCompra;
}
public void setFechaCompra(Date fechaCompra) {
	this.fechaCompra = fechaCompra;
}
public Double getPrecio() {
		return precio;
}
public void setPrecio(Double precio) {
	this.precio = precio;
}
public String getFoto() {
		return foto;
}
public void setFoto(String foto) {
	this.foto = foto;
}

	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstrumentoMusical other = (InstrumentoMusical) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}
