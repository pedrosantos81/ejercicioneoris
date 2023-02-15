package com.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.CuentaRepository;
import com.dto.CuentaProjection;
import com.excepcion.ManejoCuentaExcepcion;
import com.model.Cuenta;
import com.model.TipoCuenta;

@Service
public class CuentaServiceImpl implements CuentaService{

	@Autowired
	CuentaRepository cuentaRepository;

	@Override
	public Cuenta save(Cuenta cuenta) {
		return cuentaRepository.save(cuenta);
	}

	@Override
	public List<CuentaProjection> findAllClienteCuentas() {
		return cuentaRepository.findAllClienteCuentas();
	}

	@Override
	public Cuenta getCuentaByClienteTipoCuenta(int idcliente, TipoCuenta tipocuenta) {
		Cuenta cuenta = null;
		
		cuenta = cuentaRepository.getCuentaByClienteTipoCuenta(idcliente, tipocuenta);
		return cuenta;
	}

	@Override
	public List<Cuenta> getCuentasByCliente(int idcliente) {
		List<Cuenta> listaCuentasByCliente = cuentaRepository.getCuentasByCliente(idcliente);
		if(listaCuentasByCliente.isEmpty()) {
			throw new ManejoCuentaExcepcion("No tiene cuenta el idcliente: "+idcliente);
		}
		
		return listaCuentasByCliente;
	}

}
