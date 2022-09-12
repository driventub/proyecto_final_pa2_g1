package ec.edu.uce.repository;

import java.util.List;

import ec.edu.uce.modelo.Vehiculo;

public interface IVehiculoRepo {

	void insertar(Vehiculo vehiculo);

	void actualizar(Vehiculo vehiculo);

	void borrar(Integer id);

	Vehiculo buscar(Integer id);

	Vehiculo buscarPorPlaca(String placa);

	List<Vehiculo> buscarMarcaModelo(String marca, String modelo);
	List<Vehiculo> buscarTodos();

	boolean verificarVehiculo(Integer id);

	List<Vehiculo> buscarMarca(String marca);



}
