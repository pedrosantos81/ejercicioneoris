package com.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ClienteCuentaMovimientosProjection;
import com.dto.MovimientoDTO;
import com.excepcion.ManejoCuentaExcepcion;
import com.model.Cuenta;
import com.model.Movimientos;
import com.model.TipoCuenta;
import com.model.TipoTransaccion;
import com.service.ClienteService;
import com.service.CuentaService;
import com.service.MovimientoService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
public class MovimientoController {

	private static final Logger log = LoggerFactory.getLogger(MovimientoController.class);
	
	@Autowired
	CuentaService cuentaService;

	@Autowired
	ClienteService clienteService;

	@Autowired
	MovimientoService movimientoService;

	@GetMapping("movimientos")
	public List<ClienteCuentaMovimientosProjection> findAllMovimientos() {
		return movimientoService.getMovimientosAll();
	}

	@GetMapping("movimientos/{id}/{startDate}/{endDate}")
	public List<ClienteCuentaMovimientosProjection> findAllMovimientos(@PathVariable("id") int id,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		log.info("Movimientos de la fecha: "+startDate.toString() + "-" + endDate.toString());
		return movimientoService.findMovimientoByClienteyFecha(id, startDate, endDate);
	}
	
	@GetMapping("movimientos/{idcliente}/{tipocuenta}")
	public List<ClienteCuentaMovimientosProjection> findAllMovimientosByCliente(@PathVariable int idcliente,@PathVariable String tipocuenta) {
		return movimientoService.findAllMovimientosByCliente(idcliente, TipoCuenta.valueOf(tipocuenta));
	}

	@PostMapping("movimientos")
	public ResponseEntity<?> saveMovimiento(@Valid @RequestBody MovimientoDTO movimientoDto) {
		Movimientos movpost = null;
		Map<String, Object> response = new HashMap<String, Object>();
		Cuenta cuenta = cuentaService.getCuentaByClienteTipoCuenta(movimientoDto.getIdcliente(),
				movimientoDto.getTipoCuenta());

		log.info("--------Crea Movimiento de cuenta---------");
		Movimientos movimientos = null;
		try {
			if (cuenta != null) {
				if (movimientoDto.getTipoTransaccion() == TipoTransaccion.ABONO) {

					movimientos = new Movimientos(
							movimientoDto.getTipoTransaccion().toString() + " a cuenta " + movimientoDto.getValor(),
							movimientoDto.getValor(), cuenta.getSaldoinicial());
					cuenta.addMovimiento(movimientos);
					cuenta.setSaldoinicial(cuenta.getSaldoinicial() + movimientoDto.getValor());

				} else if (movimientoDto.getTipoTransaccion() == TipoTransaccion.RETIRO) {

					if (cuenta.getSaldoinicial() < movimientoDto.getValor()) {
						log.error("Fondos insuficientes o la cantidad a retirar excede");
						throw new ManejoCuentaExcepcion("Fondos insuficientes o la cantidad a retirar excede");
					} else if (cuenta.getSaldoinicial() >= movimientoDto.getValor()) {
						movimientos = new Movimientos(
								movimientoDto.getTipoTransaccion().toString() + " a cuenta " + movimientoDto.getValor(),
								movimientoDto.getValor(), cuenta.getSaldoinicial());
						cuenta.addMovimiento(movimientos);
						cuenta.setSaldoinicial(cuenta.getSaldoinicial() - movimientoDto.getValor());

					}

				}

				movpost = movimientoService.save(movimientos);

			} else {
				log.info("No se encontro idcliente ".concat(String.valueOf(movimientoDto.getIdcliente()).concat(" para realizar la transaccion.")));
				response.put("mensaje", "No se encontro el idcliente: "
						.concat(String.valueOf(movimientoDto.getIdcliente()).concat(" para realizar la transaccion.")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			log.error("Error al realizar el insert de movimiento en la base de datos");
			response.put("mensaje", "Error al realizar el insert de movimiento en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Movimientos>(movpost, HttpStatus.CREATED);

	}

}
