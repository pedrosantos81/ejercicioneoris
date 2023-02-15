package com.example.demo;


import java.util.ArrayList;
import java.util.Optional;

import com.model.Cliente;
import com.model.Cuenta;
import com.model.Genero;
import com.model.TipoCuenta;
import com.model.TipoIdentificacion;

public class Datos {


    public static Optional<Cliente> crearCliente001() {
        return Optional.of(new Cliente(528,"Jorge Lopez",
        		Genero.MASCULINO,
        		40,
        		"test",
        		"56444",
        		new TipoIdentificacion(20, "IFE"),
        		"pass",
        		true,1));
    }

    public static Optional<Cliente> crearCliente002() {
        return Optional.of(new Cliente(529,"Maria Lopez",
        		Genero.FEMENINO,
        		42,
        		"test",
        		"1222",
        		new TipoIdentificacion(20, "IFE"),
        		"pass",
        		true,1));
    }
    
    
    public static Optional<Cuenta> crearCuenta001() {
        return Optional.of( new Cuenta(TipoCuenta.AHORRO,3400,true)
        		);
    }

}
