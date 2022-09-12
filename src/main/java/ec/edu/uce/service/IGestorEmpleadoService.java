package ec.edu.uce.service;

import ec.edu.uce.modelo.Cliente;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.RetirarVehiculoTO;
import ec.edu.uce.modelo.Vehiculo;

public interface IGestorEmpleadoService {

	void registrarCliente(Cliente cliente);

	Cliente buscarCliente(String cedula);

	void ingresarVehiculo(Vehiculo vehiculo);

	Vehiculo buscarVehiculo(String placa);

	RetirarVehiculoTO generarTexto(String numeroReserva);

	void retirarVehiculoReservado(Reserva reserva, Vehiculo vehiculo);

	void retirarVehiculoSinReserva();

}
