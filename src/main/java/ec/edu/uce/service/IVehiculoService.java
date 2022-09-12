package ec.edu.uce.service;

import java.util.List;

import ec.edu.uce.modelo.Vehiculo;

public interface IVehiculoService {

	void insertar(Vehiculo vehiculo);

	void actualizar(Vehiculo vehiculo);

	Vehiculo buscar(Integer id);

	void borrar(Integer id);

	Vehiculo buscarPorPlaca(String placa);

	List<Vehiculo> buscarMarcaModelo(String marca, String modelo);

	List<Vehiculo> buscarTodos();

	boolean verificarVehiculo(Integer id) ;

	List<Vehiculo> buscarMarca(String marca);

	boolean verificarReserva(String placa);
}
