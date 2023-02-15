package com.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface ClienteCuentaMovimientosProjection {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	Date getFecha();
	String getCliente();
	int getNumerocuenta();
	String getTipo();
	double getSaldoinicial();
	double getMovimiento();
	double getSaldodisponible();
	boolean getEstado();
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	void setFecha(Date fecha);
	void setCliente(String nombre);
	void setNumerocuenta(int numerocuenta);
	void setTipo(String tipo);
	void setSaldoinicial(double saldoinicial);
	void setMovimiento(double movimiento);
	void setSaldodisponible(double saldodisponible);
	void setEstado(Boolean b);
	
	

}
