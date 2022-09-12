package ec.edu.uce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import ec.edu.uce.modelo.Cliente;

@SpringBootTest
@Rollback(true)
@Transactional
class ClienteServiceImplTest {

	@Autowired
	private IClienteService clienteService;
	
	@Test
	void testInsercionCliente() {
		Cliente c = new Cliente();
		c.setApellido("Canales");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('C');
		
		this.clienteService.insertarCliente(c);
		assertEquals("1789451201", this.clienteService.buscarClientePorCedula(c.getCedula()).getCedula());
	}

	@Test
	void actualizarCliente() {
		Cliente c = new Cliente();
		c.setApellido("Canales");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('C');
		
		this.clienteService.insertarCliente(c);
		Cliente clie= this.clienteService.buscarClientePorCedula(c.getCedula());
		clie.setTipoRegistro('E');
		this.clienteService.actualizarCliente(clie);
		
		assertEquals('E',clie.getTipoRegistro() );
	}
	
	@Test
	void buscarClientePorGenero() {
		Cliente c = new Cliente();
		//c.setId(1);
		c.setApellido("Canales");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('C');
		
		this.clienteService.insertarCliente(c);
		assertEquals("F", this.clienteService.buscarClientePorCedula("1789451201").getGenero());
	}
	
	@Test
	void listarClientePorApellido() {
		Cliente c = new Cliente();
		//c.setId(1);
		c.setApellido("Canales");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('C');
		
		this.clienteService.insertarCliente(c);
		
		List<Cliente> list = new ArrayList<>();
		list.add(this.clienteService.buscarClientePorCedula("1789451201"));
		
		
		assertNotNull( this.clienteService.listarClientesPorApellido("Canales"));
	}
	
	
	@Test
	void listarClientesTodos() {
		Cliente c = new Cliente();
		//c.setId(1);
		c.setApellido("Canales");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('C');
		
		this.clienteService.insertarCliente(c);
		
		List<Cliente> list = new ArrayList<>();
		list.add(this.clienteService.buscarClientePorCedula("1789451201"));
		
		
		assertNotNull( this.clienteService.listarClientes());
	}
	
	@Test
	void verificarCliente() {
		Cliente c = new Cliente();
		c.setApellido("Canales");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('C');
		
		this.clienteService.insertarCliente(c);
		assertTrue(this.clienteService.validarTipoRegistroC("1789451201"));
	}
	
	@Test
	void verificarClienteFalse() {		
		assertFalse(this.clienteService.validarTipoRegistroC("171201"));
	}
	
	
	@Test
	void verificarClieReserva() {	
		Cliente c = new Cliente();
		//c.setId(1);
		c.setApellido("Canales");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('C');
		
		this.clienteService.insertarCliente(c);
		
		List<Cliente> list = new ArrayList<>();
		list.add(this.clienteService.buscarClientePorCedula("1789451201"));
		
		
		assertEquals(1, list.size());
	}
	
	@Test
	void verificarClienteE() {
		Cliente c = new Cliente();
		c.setApellido("Canales");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('E');
		
		this.clienteService.insertarCliente(c);
		assertEquals('E', this.clienteService.buscarClientePorCedula("1789451201").getTipoRegistro());
	}
	
	
	@Test
	void verificarClienteApellido() {
		Cliente c = new Cliente();
		c.setApellido("Ruiz");
		c.setCedula("1789451201");
		c.setFechaNacimiento(LocalDateTime.of(1999, Month.APRIL,10,0,0));
		c.setGenero("F");
		c.setNombre("Mary");
		c.setReservaActiva(0);
		c.setTipoRegistro('E');
		
		this.clienteService.insertarCliente(c);
		assertEquals("Ruiz", this.clienteService.buscarClientePorCedula("1789451201").getApellido());
	}
	
	
}
