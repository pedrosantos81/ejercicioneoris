package com.controller;

import static org.hamcrest.Matchers.is;
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
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.dto.ClienteCuentaMovimientosProjection;
import com.dto.MovimientoDTO;
import com.example.demo.Datos;
import com.example.demo.EjercicioneorisApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Cuenta;
import com.model.Movimientos;
import com.model.TipoCuenta;
import com.model.TipoTransaccion;
import com.service.CuentaService;
import com.service.MovimientoService;

//@ContextConfiguration(classes = { EjercicioneorisApplication.class })
//@AutoConfigureMockMvc
//@SpringBootTest
class MovimientoControllerTest {
	
//	@Autowired
//	private MockMvc mvc;
//	
//	@MockBean
//	MovimientoService movimientoService;
//	
//	@Autowired
//	CuentaService cuentaService;
//	
//	@Autowired
//	private ObjectMapper objectMapper;
//	
//	private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
//
//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@Test
//	void testCrearMovimiento() throws Exception {
//		// Given
//		MovimientoDTO mDto = new MovimientoDTO();
//		mDto.setIdcliente(520);
//		mDto.setTipoCuenta(TipoCuenta.AHORRO);
//		mDto.setTipoTransaccion(TipoTransaccion.ABONO);
//		mDto.setValor(400d);
//		
//		when(movimientoService.save(any())).then(invocation -> {
//			Movimientos m = invocation.getArgument(0);
//			m.setIdmovimiento(1);
//			
//			return m;
//		});
//
//		System.out.println(objectMapper.writeValueAsString(mDto));
//
//		// when
//		mvc.perform(post("/movimientos/").contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(mDto)))
//				// Then
//				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andDo(print())
//				.andExpect(jsonPath("$.idcliente").value( 520))
//                .andExpect(jsonPath("$.tipoCuenta").value("AHORRO"));
////                .andExpect(jsonPath("$.cliente.nombre").value("Jorge Lopez"));
//
//		verify(movimientoService).save(any());
//	}
//	
//	@Test
//	void testObtieneMovimientosDeCliente() throws Exception{
//
//		//Given
//		List<ClienteCuentaMovimientosProjection> lista = new ArrayList<ClienteCuentaMovimientosProjection>();
//		
//		ClienteCuentaMovimientosProjection bean = factory.createProjection(ClienteCuentaMovimientosProjection.class);
//		bean.setCliente("Raul Matinez");
//		bean.setEstado(true);
//		bean.setMovimiento(450d);
//		bean.setSaldodisponible(1000d);
//		bean.setSaldoinicial(100d);
//		bean.setNumerocuenta(1);
//		bean.setFecha(new Date("02/12/2023"));
//		bean.setTipo("CORRIENTE");
//		
//		lista.add(bean);
//		
//		System.out.println(objectMapper.writeValueAsString(bean));
//		
//		//ClienteCuentaMovimientosProjection t =factory.createProjection(ClienteCuentaMovimientosProjection.class, lista);
//		
//		when(movimientoService.findAllMovimientosByCliente(528, TipoCuenta.CORRIENTE)).thenReturn(lista);
//		
//		// When
//				mvc.perform(get("/movimientos/{idcliente}/{tipocuenta}", 528,"CORRIENTE").contentType(MediaType.APPLICATION_JSON))
//						// Then
//						.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//						.andDo(print())
//						.andExpect(jsonPath("$[0].tipo").value("CORRIENTE"))
//						.andExpect(jsonPath("$[0].saldoinicial").value("100.0"))
//						.andExpect(jsonPath("$[0].numerocuenta").value(1));
//
//				verify(movimientoService).findAllMovimientosByCliente(528, TipoCuenta.CORRIENTE);
//	}

}
