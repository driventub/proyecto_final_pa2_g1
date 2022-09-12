package ec.edu.uce.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.repository.IVehiculoRepo;

@Service
public class VehiculoServicesImpl implements IVehiculoService {

	@Autowired
	private IVehiculoRepo iVehiculoRepo;

	@Override
	@Transactional
	public void insertar(Vehiculo vehiculo) {
		this.iVehiculoRepo.insertar(vehiculo);
	}

	@Override
	@Transactional
	public void actualizar(Vehiculo vehiculo) {
		this.iVehiculoRepo.actualizar(vehiculo);
	}

	@Override
	public Vehiculo buscar(Integer id) {
		return this.iVehiculoRepo.buscar(id);
	}

	@Override
	@Transactional
	public void borrar(Integer id) {
		this.iVehiculoRepo.borrar(id);
	}

	@Override
	public List<Vehiculo> buscarMarcaModelo(String marca, String modelo) {
		return this.iVehiculoRepo.buscarMarcaModelo(marca, modelo);
	}

	@Override
	public Vehiculo buscarPorPlaca(String placa) {
		return this.iVehiculoRepo.buscarPorPlaca(placa);
	}

	@Override
	public boolean verificarVehiculo(Integer id) {
		
		return this.iVehiculoRepo.verificarVehiculo(id);
	}

	@Override
	public List<Vehiculo> buscarTodos() {
		
		return this.iVehiculoRepo.buscarTodos();
	}

	@Override
	public List<Vehiculo> buscarMarca(String marca) {
	
		return this.iVehiculoRepo.buscarMarca(marca);
	}

	@Override
	public boolean verificarReserva(String placa) {
		
		Vehiculo vehiculo = this.iVehiculoRepo.buscarPorPlaca(placa);
		List<Reserva> lista = vehiculo.getReservaVehiculo();

		// Se podria eliminar por una reserva devuelto
		if (lista.size() == 0) {
			return true;
		}else{
			
			return false;

		}
		
	}

}
