package ec.edu.uce.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import ec.edu.uce.modelo.Cliente;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;

@SpringBootTest
@Transactional
@Rollback(true)
class GestorEmpleadoServiceImplTest {

	@Autowired
	private IGestorEmpleadoService gestorEmpleadoService;
	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IClienteService clienteService;
	@Autowired
	private IReservaService reservaService;
	
	@BeforeEach
	void datos(){
		Cliente c = new Cliente();
		c.setCedula("12");
		c.setApellido("Tapia");
		c.setCedula("189238203");
		c.setReservaActiva(1);
		this.gestorEmpleadoService.registrarCliente(c);
	}


	@Test
	void testRegistrarCliente() {

		Cliente c = new Cliente();
		c.setCedula("12");
		c.setApellido("Tapia");
		c.setReservaActiva(1);
		this.gestorEmpleadoService.registrarCliente(c);
		assertThat(this.gestorEmpleadoService.buscarCliente(c.getCedula())).isNotNull();

	}

	@Test
	void testBuscarCliente() {

		assertThat(this.gestorEmpleadoService.buscarCliente(this.clienteService.buscarClientePorId(5).getCedula()))
				.isNotNull();

	}

	@Test
	void testIngresarVehiculo() {
		Vehiculo v=new Vehiculo();
		v.setAnioFabricacion("1995");
		v.setAvaluo(new BigDecimal(12500));
		v.setCilindraje("1.25");
		v.setPlaca("PPP-1254");
		this.gestorEmpleadoService.ingresarVehiculo(v);
		assertThat(this.vehiculoService.buscar(v.getId())).isNotNull();
		
		
	}

	@Test
	void testBuscarVehiculo() {

		Vehiculo v1= this.gestorEmpleadoService.buscarVehiculo(this.vehiculoService.buscar(1).getPlaca());
		Vehiculo v2= this.gestorEmpleadoService.buscarVehiculo(this.vehiculoService.buscar(2).getPlaca());
		assertThat(v1.equals(v2)).isFalse();
	}

	@Test
	void testRetirarVehiculoSinReserva() {
		Vehiculo v=this.vehiculoService.buscar(1);
		Reserva r=new Reserva();
		r.setEstado('D');
		r.setNumero("123");
		r.setVehiculo(v);
		r.setCliente(this.clienteService.buscarClientePorId(1));
		r.setFechaFinal(LocalDateTime.of(2022, 10, 11, 0, 0));
		r.setFechaInicio(LocalDateTime.of(2022, 12, 11, 0, 0));
		this.reservaService.insertar(r);
		this.gestorEmpleadoService.retirarVehiculoReservado(r, v);
		assertThat(v.getEstado().equals("ND")).isTrue();
		
	}


	@Test
	void testRetirarVehiculoReservado() {
		Vehiculo v=this.vehiculoService.buscar(1);
		Reserva r=new Reserva();
		r.setEstado('D');
		r.setNumero("123");
		r.setVehiculo(v);
		r.setCliente(this.clienteService.buscarClientePorId(1));
		r.setFechaFinal(LocalDateTime.of(2022, 10, 11, 0, 0));
		r.setFechaInicio(LocalDateTime.of(2022, 12, 11, 0, 0));
		this.reservaService.insertar(r);
		this.gestorEmpleadoService.retirarVehiculoReservado(r, v);
		assertThat(r.getEstado().equals('E')).isTrue();
	}

}
