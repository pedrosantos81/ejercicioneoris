package com.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.transaction.Transactional;
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

	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public Cliente createCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente updateCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public List<ClienteProjection> getNombreClienteProjection() {
		return clienteRepository.getNombreClienteProjection();
	}

	@Override
	public Cliente findById(int id) {
		Optional<Cliente> result = clienteRepository.findById(id);

		Cliente cliente = null;
		if (result.isPresent()) {
			cliente = result.get();
		}
		else {
			throw new ClienteNotFound("No se encontro o esta dado de baja idcliente: "+id);
		}
		return cliente;
	}

	@Override
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
			throw new NoSuchElementException("No se encontro el cliente: " + id );
		}
		return cliente;
	}

	@Override
	public List<ClienteCuentaProjection> findCuentasByIdPersona(int id) {

		Optional<List<ClienteCuentaProjection>> result = clienteRepository.findCuentasByIdPersona(id);

		List<ClienteCuentaProjection> lista = null;
		if (result.get().size() < 1) {
			throw new ClienteNotFound("No se encontro el cliente :" + id);
		} else {
			lista = result.get();
		}
		return lista;
	}

	

	@Override
	public Cliente findByIdPersonaCliente(int id) {
		Optional<Cliente> result = clienteRepository.findByIdPersonaCliente(id);

		Cliente cliente = null;
		if (result.isPresent()) {
			cliente = result.get();
		} else {
			throw new ClienteNotFound("No se hallo cliente " + id);
		}
		return cliente;
	}

	@Override
	@Transactional
	public List<TipoIdentificacion> findAllTipoIdentificacion() {
		return clienteRepository.findAllTipoIdentificacion();
	}

	@Override
	public List<Cliente> findAllClientes() {
		return clienteRepository.findAllClientes();
	}

}
