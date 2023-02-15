package com.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="movimientos")
public class Movimientos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IdMovimiento")
	private int idmovimiento;
	
	@Column(name="fecha",nullable = false, updatable = false)
	@CreationTimestamp
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd MMM yyyy HH:mm:ss")
	private Date fecha;
	
	@Column(name="tipomovimiento")
	private String tipomovimiento;
	
	@Column(name="valor")
	private double valor;
	
	@Column(name="saldo")
	private double saldo;
	
	@Column(name="idCuenta",insertable = false,updatable = false)
	private int idcuenta;
	
	@ManyToOne
	@JoinColumn(name="idCuenta",referencedColumnName = "NumeroCuenta")
	@JsonIgnore
	Cuenta cuentas;

	public Movimientos() {
	}

	public Movimientos(String tipomovimiento, double valor, double saldo) {
		this.tipomovimiento = tipomovimiento;
		this.valor = valor;
		this.saldo = saldo;
	}

	public int getIdmovimiento() {
		return idmovimiento;
	}

	public void setIdmovimiento(int idmovimiento) {
		this.idmovimiento = idmovimiento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipomovimiento() {
		return tipomovimiento;
	}

	public void setTipomovimiento(String tipomovimiento) {
		this.tipomovimiento = tipomovimiento;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public int getIdcuenta() {
		return idcuenta;
	}

	public void setIdcuenta(int idcuenta) {
		this.idcuenta = idcuenta;
	}

	@JsonIgnore
	public Cuenta getCuentas() {
		return cuentas;
	}

	@JsonIgnore
	public void setCuentas(Cuenta cuentas) {
		this.cuentas = cuentas;
	}

	@Override
	public String toString() {
		return "Movimientos [idmovimiento=" + idmovimiento + ", fecha=" + fecha + ", tipomovimiento=" + tipomovimiento
				+ ", valor=" + valor + ", saldo=" + saldo + ", idcuenta=" + idcuenta + ", cuentas=" + cuentas + "]";
	}

	

}
