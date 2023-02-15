package com.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dto.ClienteCuentaMovimientosProjection;
import com.model.Movimientos;
import com.model.TipoCuenta;

public interface MovimientoRepository extends JpaRepository<Movimientos, Integer> {
	
	static String queryCuentaMovimientosAllClientes="select "
			+ "m.fecha as fecha, "
			+ "p.nombre as cliente, "
			+ "c.numerocuenta as numerocuenta, "
			+ "c.tipocuenta as tipo, "
			+ "m.saldo as saldoinicial, "
			+ "m.valor as movimiento,  "
			+ "c.saldoinicial as saldodisponible,  "
			+ "S.estado as estado "
			+ "from Cliente S join Persona p on S.idpersona=p.id "
			+ "join Cuenta c on c.idcliente=S.idcliente "
			+ "join Movimientos m on m.idcuenta=c.numerocuenta";
	
	static String queryCuentaMovimientosByCliente="select "
			+ "m.fecha as fecha, "
			+ "p.nombre as cliente, "
			+ "c.numerocuenta as numerocuenta, "
			+ "c.tipocuenta as tipo, "
			+ "m.saldo as saldoinicial, "
			+ "m.valor as movimiento,  "
			+ "c.saldoinicial as saldodisponible,  "
			+ "c.statuscuenta as estado  "
			+ "from Cliente S join Persona p on S.idpersona=p.id "
			+ "join Cuenta c on c.idcliente=S.idcliente "
			+ "join Movimientos m on m.idcuenta=c.numerocuenta "
			+ "where S.idcliente=:id "
			+ "and DATE(m.fecha) between :startDate and :endDate";
	
	static String queryMovimientosByClienteAndTipoCuenta="select "
			+ "m.fecha as fecha, "
			+ "p.nombre as cliente, "
			+ "c.numerocuenta as numerocuenta, "
			+ "c.tipocuenta as tipo, "
			+ "m.saldo as saldoinicial, "
			+ "m.valor as movimiento,  "
			+ "c.saldoinicial as saldodisponible,  "
			+ "c.statuscuenta as estado  "
			+ "from Cliente S join Persona p on S.idpersona=p.id "
			+ "join Cuenta c on c.idcliente=S.idcliente "
			+ "join Movimientos m on m.idcuenta=c.numerocuenta "
			+ "where c.idcliente=:idcliente and c.tipocuenta=:tipocuenta ";
			
	
	@Query(queryCuentaMovimientosAllClientes)
	public List<ClienteCuentaMovimientosProjection> getMovimientosAll();
	
	@Query(queryCuentaMovimientosByCliente)
	public List<ClienteCuentaMovimientosProjection> findMovmientoByClienteyFecha(int id,@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(queryMovimientosByClienteAndTipoCuenta)
	public List<ClienteCuentaMovimientosProjection> findAllMovimientosByCliente(int idcliente,TipoCuenta tipocuenta);

}
