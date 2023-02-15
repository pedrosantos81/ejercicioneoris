package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CuentaDTO;
import com.dto.CuentaProjection;
import com.model.Cliente;
import com.model.Cuenta;
import com.model.TipoCuenta;
import com.service.ClienteService;
import com.service.CuentaService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
public class CuentaController {

	@Autowired
	CuentaService cuentaService;

	@Autowired
	ClienteService clienteService;

	@GetMapping("cuentas")
	public List<CuentaProjection> findAllCuentas() {
		return cuentaService.findAllClienteCuentas();
	}
	
	@GetMapping("cuentas/{idcliente}")
	public List<Cuenta> findCuentasByIdCliente(@PathVariable int idcliente){
		return cuentaService.getCuentasByCliente(idcliente);
	}

	@PostMapping("cuentas/{idcliente}")
	public ResponseEntity<?> createCuenta(@PathVariable int idcliente, @Valid @RequestBody Cuenta cuenta) {
		Map<String, Object> response = new HashMap<>();
		Cuenta cuentaRequest, post = null;
		Cliente clienteExistente = clienteService.findById(idcliente);

		if (clienteExistente == null) {
			response.put("mensaje", "No se pudo crear cuenta:".concat(String.valueOf(cuenta.getTipocuenta())
					.concat(",no existe cliente con idcliente: ").concat(String.valueOf(idcliente))));
			response.put("error", "No se pudo crear cuenta de cliente");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			System.out.println("cliente existente: " + clienteExistente.toString() + "-idcliente: "
					+ clienteExistente.getIdcliente());
			cuentaRequest = cuentaService.getCuentaByClienteTipoCuenta(clienteExistente.getIdcliente(),
					cuenta.getTipocuenta());

			if (cuentaRequest == null) {
				cuentaRequest = new Cuenta(cuenta.getTipocuenta(), cuenta.getSaldoinicial(), true);
				cuentaRequest.setCliente(clienteExistente);
				post = cuentaService.save(cuentaRequest);
			} else if (cuentaRequest != null) {
				response.put("mensaje", "La cuenta a crear ya existe");
				response.put("error", "No se pudo crear el registro en la bd");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (DataAccessException dexc) {
			response.put("mensaje", dexc.getMessage().concat(": ").concat(dexc.getMostSpecificCause().getMessage()));
			response.put("error", "No se pudo crear el registro en la bd");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La cuenta ha sido creada con éxito!");
		response.put("cuenta", post);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@PutMapping("cuentas/{idcliente}/{tipocuenta}")
	public ResponseEntity<?> updateCuenta(@PathVariable int idcliente, @PathVariable String tipocuenta,
			@Valid @RequestBody Cuenta cuenta) {
		Map<String, Object> response = new HashMap<>();
		Cuenta cuentaRequest, cuentaPut = null;
		Cliente clienteExistente = clienteService.findById(idcliente);

		if (clienteExistente == null) {
			response.put("mensaje", "No existe idcliente: ".concat(String.valueOf(idcliente)));
			response.put("error", "No se pudo obtener cliente");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		System.out.println("cliente existente: " + clienteExistente.toString());
		try {

			cuentaRequest = cuentaService.getCuentaByClienteTipoCuenta(idcliente, TipoCuenta.valueOf(tipocuenta));

			if (cuentaRequest == null) {
				response.put("mensaje", "No se pudo actualizar cuenta:".concat(String.valueOf(cuenta.getTipocuenta())
						.concat(",o no existe cliente con idcliente: ").concat(String.valueOf(idcliente))));
				response.put("error", "No se pudo actualizar cuenta no existente");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			} else if (cuentaRequest != null) {
				cuentaRequest.setSaldoinicial(cuenta.getSaldoinicial());
				cuentaRequest.setTipocuenta(cuenta.getTipocuenta());
				cuentaPut = cuentaService.save(cuentaRequest);
			}

		} catch (DataAccessException exc) {
			response.put("mensaje", exc.getMessage().concat(": ").concat(exc.getMostSpecificCause().getMessage()));
			response.put("error", "No se pudo actualizar el registro en la bd");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La cuenta se actualizo con éxito!");
		response.put("cuenta", cuentaPut);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

}
