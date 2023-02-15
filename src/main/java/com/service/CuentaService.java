package com.service;

import java.util.List;

import com.dto.CuentaProjection;
import com.model.Cuenta;
import com.model.TipoCuenta;

public interface CuentaService {
	
	Cuenta save(Cuenta cuenta);
	List<CuentaProjection> findAllClienteCuentas();
	Cuenta getCuentaByClienteTipoCuenta(int idcliente,TipoCuenta tipocuenta);
	List<Cuenta> getCuentasByCliente(int idcliente);
}
