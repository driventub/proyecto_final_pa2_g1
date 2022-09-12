package ec.edu.uce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import ec.edu.uce.modelo.Cliente;
import ec.edu.uce.modelo.Pago;
import ec.edu.uce.modelo.ReporteClienteVIPTO;
import ec.edu.uce.modelo.ReporteReservas;
import ec.edu.uce.modelo.ReporteVehiculosVIPTO;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;



@SpringBootTest
@Rollback(true)
@Transactional
public class ReservaServiceImplTest {

    @Autowired
	private IReservaService reservaService;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Autowired
	private IPagoService pagoService;

	LocalDateTime fecha = LocalDateTime.now();

	@Test
	void insercionReserva() {

		Reserva rt = this.reservaService.buscarPorNumero("202");

		assertEquals('G', rt.getEstado());

	}

	@Test
	void actualizacionReserva() {

		Reserva rt = this.reservaService.buscarPorNumero("202");
		rt.setEstado('R');// retirado

		this.reservaService.actualizar(rt);

		Reserva ra = this.reservaService.buscarPorNumero("202");

		assertEquals('R', ra.getEstado());

	}

	@Test
	void eliminacionReserva() {

		Reserva rt = this.reservaService.buscarPorNumero("202");

		this.reservaService.borrar(rt.getId());

		List<Reserva> lista = this.reservaService.todasReservas();

		List<Reserva> lista2 = lista.stream()
				.filter(lis -> (lis.getCliente().getCedula().equals("1726117672")))
				.collect(Collectors.toList());

		assertEquals(1, lista2.size());

	}

	@Test
	void buscarIdReserva() {

		Reserva rt = this.reservaService.buscarPorNumero("203");
		int testId = rt.getId();

		Reserva rt2 = this.reservaService.buscar(testId);

		assertEquals(fecha.plusMonths(1).plusDays(7), rt2.getFechaFinal());

	}

	@Test
	void buscarPorVehiculoReserva() {

		Vehiculo v = this.vehiculoService.buscarPorPlaca("PCD-154");

		List<Reserva> listab = this.reservaService.buscarPorVehiculo(v);

		assertEquals(2, listab.size());

	}

	@Test
	void todasReserva() {

		List<Reserva> lista = this.reservaService.todasReservas();

		List<Reserva> lista2 = lista.stream()
				.filter(lis -> (lis.getCliente().getCedula().equals("1726117672")))
				.collect(Collectors.toList());

		assertEquals(2, lista2.size());
	}
	
	@Test
	void clientesVipVacioReserva() {
		List<ReporteClienteVIPTO> listaVip=this.reservaService.busquedaClientesVip();
		
		assertThat(listaVip.size()>0);
	}

	@Test
	void reporteReserva() {

		List<ReporteReservas> listac = this.reservaService.reporteReservas(fecha.minusDays(1),
				fecha.plusMonths(1).plusDays(8));

		List<ReporteReservas> lista2 = listac.stream().filter(lis -> (lis.getCedula().equals("1726117672")))
				.collect(Collectors.toList());

		assertEquals(2, lista2.size());
	}
	
	@Test
	void vehiculosVipReserva() {
		
		List<ReporteVehiculosVIPTO> lista=this.reservaService.buscarMesAnio("09", "2022");
		
		List<ReporteVehiculosVIPTO> lista2 = lista.stream()
				.filter(lis -> (lis.getVehiculo().getPlaca().equals("PCD-154")))
				.collect(Collectors.toList());
		
		assertEquals(1, lista2.size());
	}
	
	@Test
	void clientesVipReserva() {
		List<ReporteClienteVIPTO> listaVip=this.reservaService.busquedaClientesVip();
		
		List<ReporteClienteVIPTO> listaVipF=listaVip.stream()
		.filter(lis -> (lis.getCliente().getCedula().equals("1726117672")))
		.collect(Collectors.toList());
		
		assertEquals(1, listaVipF.size());
	}
	
	
	

	@BeforeEach
	void datos() {

		Cliente c = new Cliente();
		c.setNombre("Carlos");
		c.setApellido("Montalvo");
		c.setCedula("1726117672");
		c.setFechaNacimiento(LocalDateTime.of(2000, 01, 4, 4, 20));
		c.setGenero("M");
		c.setTipoRegistro('C');
		c.setReservaActiva(1);
		this.clienteService.insertarCliente(c);

		Vehiculo v = new Vehiculo();
		v.setPlaca("PCD-154");
		v.setModelo("Tracker");
		v.setMarca("Chevrolet");
		v.setAnioFabricacion("2019");
		v.setPais("Vietnam");
		v.setCilindraje("1796 cc");
		v.setAvaluo(new BigDecimal(27500.00));
		v.setValorPorDia(new BigDecimal(75.98));
		v.setEstado("D");
		this.vehiculoService.insertar(v);

		Pago p = new Pago();
		p.setValorSubTotal(new BigDecimal(100));
		p.setValorIVA(new BigDecimal(12));
		p.setValorTotalAPagar(new BigDecimal(120));
		p.setFechaCobro(fecha.plusDays(14));
		p.setTarjeta("1234212");
		this.pagoService.insertar(p);

		Reserva r = new Reserva();
		r.setEstado('G');
		r.setFechaInicio(fecha);
		r.setFechaFinal(fecha.plusDays(7));
		r.setNumero("202");
		r.setCliente(c);
		r.setVehiculo(v);
		r.setPagos(p);
		this.reservaService.insertar(r);

		Reserva r2 = new Reserva();
		r2.setEstado('G');
		r2.setFechaInicio(fecha.plusMonths(1));
		r2.setFechaFinal(fecha.plusMonths(1).plusDays(7));
		r2.setNumero("203");
		r2.setCliente(c);
		r2.setVehiculo(v);
		r2.setPagos(p);
		this.reservaService.insertar(r2);

	}

}
