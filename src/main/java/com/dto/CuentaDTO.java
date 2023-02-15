package com.dto;

import java.io.Serializable;

public class CuentaDTO implements Serializable{
	
	private int idcliente;
	private String tipocuenta;
	private double saldoinicial;
	private boolean status;
	
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public String getTipocuenta() {
		return tipocuenta;
	}
	public void setTipocuenta(String tipocuenta) {
		this.tipocuenta = tipocuenta;
	}
	public double getSaldoinicial() {
		return saldoinicial;
	}
	public void setSaldoinicial(double saldoinicial) {
		this.saldoinicial = saldoinicial;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CuentaDTO [idcliente=" + idcliente + ", ticuenta=" + tipocuenta + ", saldoinicial=" + saldoinicial
				+ ", status=" + status + "]";
	}
	
	
	

}
