package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dto.CuentaProjection;
import com.model.Cuenta;
import com.model.TipoCuenta;

public interface CuentaRepository extends JpaRepository<Cuenta,Integer>{
	
	static String queryClienteCuenta="select p.nombre as nombre, "
			+ "c.numerocuenta as numerocuenta, "
			+ "c.tipocuenta as tipocuenta, "
			+ "c.saldoinicial as saldoinicial, "
			+ "c.statuscuenta as statuscuenta "
			+ "from Cliente S join Persona p on S.idpersona=p.id "
			+ "join Cuenta c on c.idcliente=S.idcliente";
	
	static String getCuentaPorClienteyTipoCuenta="select c from Cuenta c "
			+ "where c.idcliente=:idcliente and c.tipocuenta=:tipocuenta";
	
	static String getCuentasPorCliente="select c from Cuenta c"
			+ " where c.idcliente=:idcliente";
	
	
	@Query(queryClienteCuenta)
	public List<CuentaProjection> findAllClienteCuentas();
	
	@Query(getCuentaPorClienteyTipoCuenta)
	//public Optional<Cuenta> getCuentaByClienteTipoCuenta(int idcliente,TipoCuenta tipocuenta);
	public Cuenta getCuentaByClienteTipoCuenta(int idcliente,TipoCuenta tipocuenta);
	
	@Query(getCuentasPorCliente)
	public List<Cuenta> getCuentasByCliente(int idcliente);
	

}
