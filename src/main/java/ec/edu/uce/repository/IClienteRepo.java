package ec.edu.uce.repository;

import java.util.List;

import ec.edu.uce.modelo.Cliente;

public interface IClienteRepo {

	void insertar(Cliente cliente);

	void actualizar(Cliente cliente);

	Cliente buscar(Integer id);

	Cliente buscarCedula(String cedula);

	void borrar(Integer id);

	List<Cliente> listarClientes();

	List<Cliente> buscarPorApellido(String apellido);

	boolean verificarReserva(Integer id);
	
	boolean validarTipoRegistroC(String cedula);

}
