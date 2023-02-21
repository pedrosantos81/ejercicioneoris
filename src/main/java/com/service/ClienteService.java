package com.service;


import java.util.List;

import com.dto.ClienteCuentaProjection;
import com.dto.ClienteProjection;
import com.model.Cliente;
import com.model.TipoIdentificacion;


public interface ClienteService {

	Cliente createOrUpdateCliente(Cliente cliente);
	List<Cliente> findAllClientes();
	void delete(int id);
	List<ClienteProjection> getNombreClienteProjection();
	Cliente findByIdCliente(int idcliente);
	Cliente findByIdPersona(int id);
	List<ClienteCuentaProjection> findCuentasByIdPersona(int id);
	List<TipoIdentificacion> findAllTipoIdentificacion();

}
