package com.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotEmpty
	@Column(name = "nombre", nullable = false)
	@Size(min = 4, message = "El tamaño debe ser mayor a 4 caracteres")
	private String nombre;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "no puede ser vacia")
	@Column(name = "genero", nullable = false)
	private Genero genero;

	@Column(name = "edad", nullable = false)
	@Min(18)
	private int edad;

	@NotEmpty
	@Column(name = "direccion", nullable = false)
	private String direccion;

	@NotEmpty
	@Column(name = "telefono", nullable = false)
	private String telefono;

	@OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
	private Cliente cliente;

	@NotNull(message = "no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "identificacion", referencedColumnName = "id_tipoidenficacion")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	TipoIdentificacion tipoIdentificacion;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Persona() {

	}
	
	

	public Persona(@NotEmpty @Size(min = 4, message = "El tamaño debe ser mayor a 4 caracteres") String nombre,
			@NotNull(message = "no puede ser vacia") Genero genero, 
			@Min(18) int edad, 
			@NotEmpty String direccion,
			@NotEmpty String telefono,
			@NotNull(message = "no puede ser vacia") TipoIdentificacion tipoIdentificacion) {
		super();
		this.nombre = nombre;
		this.genero = genero;
		this.edad = edad;
		this.direccion = direccion;
		this.telefono = telefono;
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", genero=" + genero + ", edad=" + edad
				+ ", identificacion=" + tipoIdentificacion.getIdidentificacion() + ", direccion=" + direccion
				+ ", telefono=" + telefono + "]";
	}

}
