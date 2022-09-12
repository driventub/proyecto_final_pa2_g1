package ec.edu.uce.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Cliente;
import ec.edu.uce.repository.IClienteRepo;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteRepo iClienteRepo;


	@Override
	@Transactional
	public void insertarCliente(Cliente cliente) {
		this.iClienteRepo.insertar(cliente);
	}

	@Override
	@Transactional
	public void actualizarCliente(Cliente cliente) {
		this.iClienteRepo.actualizar(cliente);
	}

	@Override
	public Cliente buscarClientePorId(Integer id) {
		return this.iClienteRepo.buscar(id);
	}

	@Override
	@Transactional
	public void borrarClientePorId(Integer id) {
		this.iClienteRepo.borrar(id);
	}

	@Override
	public Cliente buscarClientePorCedula(String cedula) {
		return this.iClienteRepo.buscarCedula(cedula);
	}
	
	@Override
	public List<Cliente> listarClientesPorApellido(String apellido) {
		
		return this.iClienteRepo.buscarPorApellido(apellido);
	}
	

	@Override
	public List<Cliente> listarClientes() {		
		return this.iClienteRepo.listarClientes();
	}

	@Override
	public boolean verificarReserva(Integer id) {
		
		return this.iClienteRepo.verificarReserva(id);
	}

	@Override
	public boolean validarTipoRegistroC(String cedula) {
		// TODO Auto-generated method stub
		return this.iClienteRepo.validarTipoRegistroC(cedula);
	}



}
