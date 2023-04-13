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



@Entity
@Table(name="banda")
public class Banda implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Column(name="nombre")
private String nombre;


@Column(name="componentes")
private Integer componentes;


@Column(name="foto")
private String foto;



	
	public Banda() {}


	public Long getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public Integer getComponentes() {
		return componentes;
}
public void setComponentes(Integer componentes) {
	this.componentes = componentes;
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
		Banda other = (Banda) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}
