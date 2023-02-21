package com.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.dto.ClienteCuentaProjection;
import com.dto.ClienteProjection;

import com.model.Cliente;

import com.model.TipoIdentificacion;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	static String queryStr3="select p.id as id,"
			+ "p.nombre as nombre,"
			+ "p.telefono as telefono, "
			+ "p.direccion as direccion,"
			+ "S.estado as estado,S.pass as pass "
			+ "from Cliente S join Persona p on S.idpersona=p.id ";
			

	
	static String queryUpdaCliente ="update Cliente c set c.nombre=:nombre"
			+ ", c.genero=:genero"
			+ ", c.direccion=:direcion"
			+ ", c.telefono=:telefono"
			+ ", c.edad=:edad"
			+ ", c.pass=:pass"
			
			+ " WHERE c.idpersona = :id";
	
	static String queryBajaCliente="update Cliente c set c.estado=0 where c.idpersona=:id";
	
	@Query(queryStr3)
	public List<ClienteProjection> getNombreClienteProjection();
	
	@Query("select c from Cliente c where c.idcliente=:idcliente and c.estado=1")
	public Optional<Cliente> findByIdCliente(int idcliente);
	
	@Query("select c from Cliente c where c.idpersona=:id")
	public Optional<Cliente> findByIdPersona(int id);
	
	@Query("select a.numerocuenta as numerocuenta, c.idcliente as idcliente, "
		  + "a.saldoinicial as saldoinicial, a.tipocuenta as tipocuenta, "
		  + "c.persona.nombre as nombre "
	      +" from Cliente c join Cuenta a on c.idcliente=a.idcliente where c.idpersona=:id")
	public Optional<List<ClienteCuentaProjection>> findCuentasByIdPersona(int id);
    
    @Query("from Cliente ")
    public List<Cliente> findAllClientes();
    
    @Modifying
    @Transactional
    @Query(queryBajaCliente)
    void delete(int id);
    
    @Query("from TipoIdentificacion")
    public List<TipoIdentificacion> findAllTipoIdentificacion(); 
	
}