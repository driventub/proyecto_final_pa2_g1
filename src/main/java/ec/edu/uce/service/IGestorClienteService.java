package ec.edu.uce.service;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.uce.modelo.Cliente;
import ec.edu.uce.modelo.Pago;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.ReservarVehiculoTO;
import ec.edu.uce.modelo.Vehiculo;

public interface IGestorClienteService {

	// List<String> buscarVehiculosDisponiblesTexto(String marca, String modelo);

	List<Vehiculo> buscarVehiculosDisponibles(String marca, String modelo);

	Reserva reservarVehiculo(String placa, String cedulaCliente, LocalDateTime fechaInicio, LocalDateTime fechaFinal,
			String numeroTarjeta);

	void registrarCliente(Cliente cliente);

	boolean verificarDisponibilidad(ReservarVehiculoTO reservarVehiculoTO);

	Pago generarPago(String placa, LocalDateTime fechaInicio, LocalDateTime fechaFinal);

	Reserva crearReserva(ReservarVehiculoTO reservarVehiculoTO);

}
