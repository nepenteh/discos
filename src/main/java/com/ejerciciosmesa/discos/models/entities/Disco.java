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
@Table(name="disco")
public class Disco implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Column(name="titulo")
private String titulo;


@NotBlank
@Column(name="autor")
private String autor;


@Column(name="anolanzamiento")
private Integer anoLanzamiento;


@Column(name="caratula")
private String caratula;



	
	public Disco() {}


	public Long getId() {
		return id;
	}


	public String getTitulo() {
		return titulo;
}
public void setTitulo(String titulo) {
	this.titulo = titulo;
}
public String getAutor() {
		return autor;
}
public void setAutor(String autor) {
	this.autor = autor;
}
public Integer getAnoLanzamiento() {
		return anoLanzamiento;
}
public void setAnoLanzamiento(Integer anoLanzamiento) {
	this.anoLanzamiento = anoLanzamiento;
}
public String getCaratula() {
		return caratula;
}
public void setCaratula(String caratula) {
	this.caratula = caratula;
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
		Disco other = (Disco) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}
