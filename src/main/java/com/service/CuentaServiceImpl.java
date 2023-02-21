package com.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.CuentaRepository;
import com.dto.CuentaProjection;
import com.excepcion.ManejoCuentaExcepcion;
import com.model.Cuenta;
import com.model.TipoCuenta;

@Service
public class CuentaServiceImpl implements CuentaService{

	private static final Logger log = LoggerFactory.getLogger(CuentaServiceImpl.class);
	
	@Autowired
	CuentaRepository cuentaRepository;

	@Override
	@Transactional
	public Cuenta save(Cuenta cuenta) {
		return cuentaRepository.save(cuenta);
	}

	@Override
	public List<CuentaProjection> findAllClienteCuentas() {
		return cuentaRepository.findAllClienteCuentas();
	}

	@Override
	public Cuenta getCuentaByClienteTipoCuenta(int idcliente, TipoCuenta tipocuenta) {
		log.info("getCuentaByClienteTipoCuenta: "+idcliente+","+tipocuenta.name());
		cuentaRepository.getCuentaByClienteTipoCuenta(idcliente, tipocuenta);
		return cuentaRepository.getCuentaByClienteTipoCuenta(idcliente, tipocuenta);
	}

	@Override
	public List<Cuenta> getCuentasByCliente(int idcliente) {
		List<Cuenta> listaCuentasByCliente = cuentaRepository.getCuentasByCliente(idcliente);
		if(listaCuentasByCliente.isEmpty()) {
			log.debug("No tiene cuenta el idcliente: "+idcliente);
			throw new ManejoCuentaExcepcion("No tiene cuenta el idcliente: "+idcliente);
		}
		
		return listaCuentasByCliente;
	}

}
