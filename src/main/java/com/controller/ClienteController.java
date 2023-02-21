package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ClienteProjection;
import com.model.Cliente;
import com.model.TipoIdentificacion;
import com.service.ClienteService;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
@RestController
@Validated
public class ClienteController {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	@Autowired
	ClienteService clienteService;

	@GetMapping("/clientes")
	public List<Cliente> findAllClientes(){
		log.info("Obtiene todos los clientes");
		return clienteService.findAllClientes();
	}
	
	@GetMapping("/clientes/{idcliente}")
	public ResponseEntity<?> show(@PathVariable int idcliente) {
		Cliente cliente=null;
		Map <String,Object> response = new HashMap<>();
		log.info("-----Obtiene cliente----");
		try {
			cliente = clienteService.findByIdCliente(idcliente);
		}catch(DataAccessException d) {
			log.error("Error al consultar informacion en bd");
			response.put("mensaje", "Error al consultar la bd.");
			response.put("error", d.getMessage().concat(": ").concat(d.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente){
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();
        log.info("-----Entro a crear cliente----");
		try {
			cliente.setEstado(true);
			clienteNew = clienteService.createOrUpdateCliente(cliente);
		}catch(DataAccessException e) {
			log.error(e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("El cliente ha sido creado con éxito!");
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = "clientes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ClienteProjection> getClientesporcampo() {
		return clienteService.getNombreClienteProjection();
	}
	
	@DeleteMapping("clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePost(@PathVariable("id") int id) {
		log.info("------Entro a borrar cliente------".concat(String.valueOf(id)));
		Cliente cliente = clienteService.findByIdPersona(id);

		clienteService.delete(cliente.getId());
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> updateCliente(@PathVariable int id,@Valid @RequestBody Cliente cliente) {
		Cliente clienteActual = clienteService.findByIdPersona(id);
		Cliente clientepost = null;
		Map<String,Object> response = new HashMap<>();
		log.info("------Entro a actualizar cliente------".concat(String.valueOf(id)));
		
		if(clienteActual==null)
		{
			log.error("Error: No se pudo editar el cliente ID:".concat(String.valueOf(id).concat(" no existe en la bd.")));
			response.put("mensaje", "Error: No se pudo editar el cliente ID:".concat(String.valueOf(id).concat(" no existe en la bd.")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setGenero(cliente.getGenero());
			clienteActual.setTelefono(cliente.getTelefono());
			clienteActual.setDireccion(cliente.getDireccion());
			clienteActual.setEdad(cliente.getEdad());
			clienteActual.setEstado(true);
			clienteActual.setPass(cliente.getPass());
			clienteActual.setTipoIdentificacion(cliente.getTipoIdentificacion());
			clientepost = clienteService.createOrUpdateCliente(clienteActual);
			
		}catch(DataAccessException dexc) {
			log.error("No se pudo actualizar la bd");
			response.put("message", dexc.getMessage().concat(": ").concat(dexc.getMostSpecificCause().getMessage()));
			response.put("error","No se pudo actualizar la bd");
			new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		log.info("El cliente se actualizo con exito");
		response.put("message", "El cliente se actualizo con exito");
		response.put("cliente", clientepost);

		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);

	}
	
	@GetMapping("/clientes/cat_identificaciones")
	public List<TipoIdentificacion> getTipoIdentificacion(){
		return clienteService.findAllTipoIdentificacion();
	}
	

}
