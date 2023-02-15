package com.controller;

import static com.example.demo.Datos.crearCuenta001;
import static org.hamcrest.Matchers.hasSize;
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

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;

import com.controller.ClienteController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Cliente;
import com.model.Genero;
import com.model.TipoIdentificacion;
import com.service.ClienteService;
import com.example.demo.*;

@ContextConfiguration(classes = { EjercicioneorisApplication.class })
@AutoConfigureMockMvc

@SpringBootTest
class ClienteControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ClienteService clienteService;

	@Autowired
	private ObjectMapper objectMapper;

//	@BeforeEach
//    void setUp() {
//        objectMapper = new ObjectMapper();
//    }

	@Test
	void testDetalleCliente() throws Exception {
		// Given
		when(clienteService.findById(1)).thenReturn(Datos.crearCliente001().orElseThrow(null));

		// When
		mvc.perform(get("/clientes/{idcliente}", 1).contentType(MediaType.APPLICATION_JSON))
				// Then
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$.nombre").value("Jorge Lopez")).andExpect(jsonPath("$.genero").value("MASCULINO"))
				.andExpect(jsonPath("$.idpersona", is(1)));

		verify(clienteService).findById(1);
	}

	@Test
	public void guardarCliente() throws Exception {
		// given

		Cliente cliente = new Cliente(0, "Jorge Lopez", Genero.MASCULINO, 43, "test", "56444",
				new TipoIdentificacion(20, "IFE"), "pass", true, 1);
		when(clienteService.createCliente(any())).then(invocation -> {
			Cliente c = invocation.getArgument(0);
			c.setIdcliente(1);
			return c;
		});

		// when
		mvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cliente)))
				// Then
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(jsonPath("$.cliente.id", is(1)))
				.andExpect(jsonPath("$.cliente.genero").value("MASCULINO"))
				.andExpect(jsonPath("$.cliente.nombre").value("Jorge Lopez"));

		verify(clienteService).createCliente(any());

	}

	@Test
	void testListar() throws Exception {
		// Given
		List<Cliente> cuentas = Arrays.asList(Datos.crearCliente001().orElseThrow(null),
				Datos.crearCliente002().orElseThrow(null));
		when(clienteService.findAllClientes()).thenReturn(cuentas);

		// When
		mvc.perform(get("/clientes/").contentType(MediaType.APPLICATION_JSON))
				// Then
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(jsonPath("$[0].nombre").value("Jorge Lopez"))
				.andExpect(jsonPath("$[1].nombre").value("Maria Lopez")).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(content().json(objectMapper.writeValueAsString(cuentas)));

		verify(clienteService).findAllClientes();
	}

}
