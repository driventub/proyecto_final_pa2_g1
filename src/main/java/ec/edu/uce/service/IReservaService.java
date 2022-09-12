package ec.edu.uce.service;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.uce.modelo.ReporteClienteVIPTO;
import ec.edu.uce.modelo.ReporteReservas;
import ec.edu.uce.modelo.ReporteVehiculosVIPTO;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;

public interface IReservaService {

	void insertar(Reserva reserva);

	void actualizar(Reserva reserva);

	Reserva buscar(Integer id);

	void borrar(Integer id);

	Reserva buscarPorNumero(String numero);

	List<Reserva> buscarPorVehiculo(Vehiculo vehiculo);

	List<ReporteReservas> reporteReservas(LocalDateTime fechaInicio, LocalDateTime fechaFinal);

	List<Reserva> todasReservas();

	List<ReporteVehiculosVIPTO> buscarMesAnio(String mes, String anio);

	public List<ReporteClienteVIPTO> busquedaClientesVip() ;
}
