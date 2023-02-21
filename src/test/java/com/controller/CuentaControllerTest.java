package com.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Datos;
import com.example.demo.EjercicioneorisApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Cliente;
import com.model.Cuenta;
import com.model.Genero;
import com.model.TipoCuenta;
import com.model.TipoIdentificacion;
import com.service.ClienteService;
import com.service.CuentaService;

@ActiveProfiles({ "test" })

@ContextConfiguration(classes = { EjercicioneorisApplication.class })
@AutoConfigureMockMvc

@SpringBootTest
class CuentaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CuentaService cuentaService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testDetalleCuentas() throws Exception {
		// Given
		Cuenta c = Datos.crearCuenta001().get();
		c.setIdcliente(528);
		c.setCliente(Datos.crearCliente001().get());
		List<Cuenta> listaCuentas = new ArrayList<Cuenta>();
		listaCuentas.add(c);
		System.out.println(objectMapper.writeValueAsString(listaCuentas));

		when(cuentaService.getCuentasByCliente(528)).thenReturn(listaCuentas);

		// When
		mvc.perform(get("/cuentas/{idcliente}", 528).contentType(MediaType.APPLICATION_JSON))
				// Then
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$[0].clienteName").value("Jorge Lopez"))
				.andExpect(jsonPath("$[0].saldoinicial").value("3400.0"))
				.andExpect(jsonPath("$[0].idcliente", is(528)));

		verify(cuentaService).getCuentasByCliente(528);
	}

	@Test
	void testCrearCuenta() throws Exception {
		Cuenta cuenta = new Cuenta(TipoCuenta.AHORRO, 3000, true);
		cuenta.setCliente(Datos.crearCliente001().get());

		when(cuentaService.save(any())).then(invocation -> {
			Cuenta c = invocation.getArgument(0);
			c.setNumerocuenta(1);
			return c;
		});

		System.out.println(objectMapper.writeValueAsString(cuenta));

		// when
		mvc.perform(post("/cuentas/{idcliente}", 528).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta)))
				// Then
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(jsonPath("$.cuenta.numerocuenta", is(1)));
//                .andExpect(jsonPath("$.cliente.genero").value("MASCULINO"))
//                .andExpect(jsonPath("$.cliente.nombre").value("Jorge Lopez"));

		verify(cuentaService).save(any());

	}

}
