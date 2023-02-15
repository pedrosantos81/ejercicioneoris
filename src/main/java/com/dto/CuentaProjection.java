package com.dto;

import com.model.TipoCuenta;

public interface CuentaProjection {
	
	String getNombre();
	int getNumerocuenta();
	String getTipocuenta();
	double getSaldoinicial();
    boolean isStatuscuenta();
	

}
