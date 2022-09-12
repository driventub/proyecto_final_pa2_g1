package ec.edu.uce.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.ReporteClienteVIPTO;
import ec.edu.uce.modelo.ReporteReservas;
import ec.edu.uce.modelo.ReporteVehiculosVIPTO;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.repository.IReservaRepo;

@Service
public class ReservaServiceImpl implements IReservaService {

	@Autowired
	private IReservaRepo iReservaRepo;

	@Override
	@Transactional
	public void insertar(Reserva reserva) {
		this.iReservaRepo.insertar(reserva);
	}

	@Override
	public void actualizar(Reserva reserva) {
		this.iReservaRepo.actualizar(reserva);
	}

	@Override
	public Reserva buscar(Integer id) {
		return this.iReservaRepo.buscar(id);
	}

	@Override
	public void borrar(Integer id) {
		this.iReservaRepo.borrar(id);
	}

	@Override
	public Reserva buscarPorNumero(String numero) {
		return this.iReservaRepo.buscarPorNumero(numero);
	}

	@Override
	public List<ReporteReservas> reporteReservas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
		return this.iReservaRepo.reporteReservas(fechaInicio, fechaFinal);
	}

	@Override
	public List<Reserva> buscarPorVehiculo(Vehiculo vehiculo) {
		return this.iReservaRepo.buscarPorVehiculo(vehiculo);
	}

	@Override
	public List<Reserva> todasReservas() {
		return this.iReservaRepo.todasReservas();
	}

	@Override
	public List<ReporteVehiculosVIPTO> buscarMesAnio(String mes, String anio) {
		return this.iReservaRepo.buscarMesAnio(mes, anio);
	}

	@Override
	public List<ReporteClienteVIPTO> busquedaClientesVip() {
		
		return this.iReservaRepo.busquedaClientesVip();
	}

}
