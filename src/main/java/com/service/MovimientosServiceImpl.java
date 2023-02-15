package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.MovimientoRepository;
import com.dto.ClienteCuentaMovimientosProjection;
import com.excepcion.ManejoCuentaExcepcion;
import com.model.Movimientos;
import com.model.TipoCuenta;

@Service
public class MovimientosServiceImpl implements MovimientoService {

	@Autowired
	MovimientoRepository movimientoRepository;

	@Override
	public Movimientos save(Movimientos movimiento) {
		return movimientoRepository.save(movimiento);
	}

	@Override
	public List<ClienteCuentaMovimientosProjection> getMovimientosAll() {
		return movimientoRepository.getMovimientosAll();
	}

	@Override
	public List<ClienteCuentaMovimientosProjection> findMovimientoByClienteyFecha(int id, Date startDate, Date endDate) {

		List<ClienteCuentaMovimientosProjection> lstclienteMovimientos = movimientoRepository
				.findMovmientoByClienteyFecha(id, startDate, endDate);

		if (lstclienteMovimientos.isEmpty()) {
			throw new ManejoCuentaExcepcion("No tiene movimientos la cuenta");
		}

		return lstclienteMovimientos;
	}

	@Override
	public List<ClienteCuentaMovimientosProjection> findAllMovimientosByCliente(int idcliente, TipoCuenta tipocuenta) {
		List<ClienteCuentaMovimientosProjection> lstclienteMovimientos = movimientoRepository
				.findAllMovimientosByCliente(idcliente, tipocuenta);

		if (lstclienteMovimientos.isEmpty()) {
			throw new ManejoCuentaExcepcion("No tiene movimientos la cuenta");
		}

		return lstclienteMovimientos;
	}

}
