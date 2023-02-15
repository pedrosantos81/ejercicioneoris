package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="catalogoidentificacion")
public class TipoIdentificacion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipoidenficacion")
	private int ididentificacion;
	
	@Column(name="descripcion")
	private String tipoIdentificacion;

	public TipoIdentificacion() {
	}

	public TipoIdentificacion(int ididentificacion, String tipoIdentificacion) {
		this.ididentificacion = ididentificacion;
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
	public int getIdidentificacion() {
		return ididentificacion;
	}

	public void setIdidentificacion(int ididentificacion) {
		this.ididentificacion = ididentificacion;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
}
