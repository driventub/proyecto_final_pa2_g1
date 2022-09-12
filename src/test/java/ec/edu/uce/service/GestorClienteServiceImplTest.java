package ec.edu.uce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
@Rollback(true)
@Transactional
public class GestorClienteServiceImplTest {

    

 

    @Autowired
	private IGestorClienteService gestorClienteService;
	
	@Autowired
	private IClienteService clienteService;

    @Autowired
    private IVehiculoService vehiculoService;

    @Autowired
    private IReservaService reservaService;

    @BeforeEach
    void datos(){
        Cliente clie = new Cliente();
        clie.setCedula("1718496944");
        clie.setReservaActiva(0);
        
        this.clienteService.insertarCliente(clie);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca("PPP-222");
        vehiculo.setEstado("D");
        vehiculo.setValorPorDia(new BigDecimal("22.22"));
        this.vehiculoService.insertar(vehiculo);

        Reserva reserva = new Reserva();
        reserva.setNumero("1-CHEV");
        this.reservaService.insertar(reserva);


    }
	



	@Test
	void testBuscarVehiculosDisponibles() {
		assertThat(this.gestorClienteService.buscarVehiculosDisponibles("chevrolet", "camaro")).isNotNull();
	}

	@Test
	void testReservarVehiculo() {

		assertEquals('G',
				this.gestorClienteService.reservarVehiculo("PPP-222", "1718496944",
						LocalDateTime.of(2022, 11, 02, 00, 00), LocalDateTime.of(2022, 11, 14, 00, 00), "0123")
						.getEstado());
	}

	@Test
	void testRegistrarCliente() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Luis");
		cliente.setApellido("Ortiz");
		cliente.setCedula("1704115102");
		this.gestorClienteService.registrarCliente(cliente);
		assertEquals('C', this.clienteService.buscarClientePorCedula("1704115102").getTipoRegistro());
	}
	
	@Test
	void testRegistrarClienteDos() {
		Cliente cliente = new Cliente();
		cliente.setNombre("Miguel");
		cliente.setApellido("Carrion");
		cliente.setCedula("1796236632");
		this.gestorClienteService.registrarCliente(cliente);
		assertEquals("Carrion", this.clienteService.buscarClientePorCedula("1796236632").getApellido());
	}	

	@Test
	void testGenerarPago() {
		assertThat(this.gestorClienteService.generarPago("PPP-222", LocalDateTime.of(2022, 8, 20, 20, 00), LocalDateTime.of(2022, 8, 29, 20, 00))).isNotNull();
		
	}
    
}
