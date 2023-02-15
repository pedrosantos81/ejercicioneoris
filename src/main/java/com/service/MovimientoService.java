package com.service;

import java.util.Date;
import java.util.List;

import com.dto.ClienteCuentaMovimientosProjection;
import com.model.Movimientos;
import com.model.TipoCuenta;

public interface MovimientoService {
	
   Movimientos save(Movimientos movimiento);
   List<ClienteCuentaMovimientosProjection> getMovimientosAll();
   List<ClienteCuentaMovimientosProjection> findMovimientoByClienteyFecha(int id,Date startDate,Date endDate);
   List<ClienteCuentaMovimientosProjection> findAllMovimientosByCliente(int idcliente,TipoCuenta tipocuenta);
   

}
