package ec.edu.uce.service;

import java.util.List;

import ec.edu.uce.modelo.Cliente;

public interface IClienteService {

	void insertarCliente(Cliente cliente);

	void actualizarCliente(Cliente cliente);

	Cliente buscarClientePorId(Integer id);

	Cliente buscarClientePorCedula(String cedula);

	void borrarClientePorId(Integer id);
	
	List<Cliente> listarClientes();
	
	List<Cliente> listarClientesPorApellido(String apellido);

	boolean verificarReserva(Integer id);
	
	boolean validarTipoRegistroC(String cedula);
}
