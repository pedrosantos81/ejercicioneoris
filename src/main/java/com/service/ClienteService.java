package com.service;


import java.util.List;

import com.dto.ClienteCuentaProjection;
import com.dto.ClienteProjection;
import com.model.Cliente;
import com.model.TipoIdentificacion;


public interface ClienteService {

	Cliente createCliente(Cliente cliente);
	Cliente updateCliente(Cliente cliente);
	List<Cliente> findAllClientes();
	void delete(int id);
	List<ClienteProjection> getNombreClienteProjection();
	Cliente findById(int id);
	Cliente findByIdPersona(int id);
	Cliente findByIdPersonaCliente(int id);
	List<ClienteCuentaProjection> findCuentasByIdPersona(int id);
	List<TipoIdentificacion> findAllTipoIdentificacion();

}
