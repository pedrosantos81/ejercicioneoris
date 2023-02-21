package com.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.ClienteRepository;
import com.dto.ClienteCuentaProjection;
import com.dto.ClienteProjection;
import com.excepcion.ClienteNotFound;
import com.model.Cliente;
import com.model.TipoIdentificacion;

@Service
public class ClienteServiceImpl implements ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	@Transactional
	public Cliente createOrUpdateCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public List<ClienteProjection> getNombreClienteProjection() {
		List<ClienteProjection> lista = clienteRepository.getNombreClienteProjection();
		if(lista.isEmpty()) {
			log.debug("Lista vacia");
			throw new ClienteNotFound("Lista vacia");
		}
		return lista;
	}

	@Override
	public Cliente findByIdCliente(int idcliente) {
		Optional<Cliente> result = clienteRepository.findByIdCliente(idcliente);

		Cliente cliente = null;
		if (result.isPresent()) {
			cliente = result.get();
		}
		else {
			log.debug("No se encontro o esta dado de baja idcliente: "+idcliente);
			throw new ClienteNotFound("No se encontro o esta dado de baja idcliente: "+idcliente);
		}
		return cliente;
	}

	@Override
	@Transactional
	public void delete(int id) {
		clienteRepository.delete(id);
	}

	@Override
	public Cliente findByIdPersona(int id) {
		Optional<Cliente> result = clienteRepository.findByIdPersona(id);

		Cliente cliente = null;
		if (result.isPresent()) {
			cliente = result.get();
		} else {
			log.debug("No se encontro el cliente: "+id);
			throw new NoSuchElementException("No se encontro el cliente: " + id );
		}
		return cliente;
	}

	@Override
	public List<ClienteCuentaProjection> findCuentasByIdPersona(int id) {

		Optional<List<ClienteCuentaProjection>> result = clienteRepository.findCuentasByIdPersona(id);

		List<ClienteCuentaProjection> lista = null;
		if (result.get().size() < 1) {
			log.debug("No se encontro el cliente: "+id);
			throw new ClienteNotFound("No se encontro el cliente :" + id);
		} else {
			lista = result.get();
		}
		return lista;
	}

	@Override
	public List<TipoIdentificacion> findAllTipoIdentificacion() {
		return clienteRepository.findAllTipoIdentificacion();
	}

	@Override
	public List<Cliente> findAllClientes() {
		return clienteRepository.findAllClientes();
	}

}
