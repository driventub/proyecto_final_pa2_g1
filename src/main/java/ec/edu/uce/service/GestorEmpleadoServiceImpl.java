package ec.edu.uce.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Cliente;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.RetirarVehiculoTO;
import ec.edu.uce.modelo.Vehiculo;

@Service
public class GestorEmpleadoServiceImpl implements IGestorEmpleadoService {

	@Autowired
	private IClienteService iClienteService;

	@Autowired
	private IVehiculoService iVehiculoService;

	@Autowired
	private IReservaService iReservaService;

	@Override
	@Transactional
	public void registrarCliente(Cliente cliente) {
		cliente.setTipoRegistro('E');
		cliente.setReservaActiva(0);
		this.iClienteService.insertarCliente(cliente);
	}

	@Override
	public Cliente buscarCliente(String cedula) {
		return this.iClienteService.buscarClientePorCedula(cedula);
	}

	@Override
	public void ingresarVehiculo(Vehiculo vehiculo) {
		vehiculo.setEstado("D");
		this.iVehiculoService.insertar(vehiculo);

	}

	@Override
	public Vehiculo buscarVehiculo(String placa) {
		return this.iVehiculoService.buscarPorPlaca(placa);
	}

	@Override
	public void retirarVehiculoSinReserva() {

	}

	@Override
	public RetirarVehiculoTO generarTexto(String numeroReserva) {
		Reserva reserva = this.iReservaService.buscarPorNumero(numeroReserva);
		Cliente cliente = this.iClienteService.buscarClientePorId(reserva.getCliente().getId());
		Vehiculo vehiculo = this.iVehiculoService.buscar(reserva.getVehiculo().getId());

		String estado = (vehiculo.getEstado() == "D") ? "Disponible" : "No Disponible";
		
		String resultado = "Placa: " + vehiculo.getPlaca() + " - Modelo: " + vehiculo.getModelo() + " - Estado: "
				+ estado + " - Fecha: " + reserva.getFechaInicio().getDayOfMonth() + "/"
				+ reserva.getFechaInicio().getMonthValue() + "/" + reserva.getFechaInicio().getYear() + "-"
				+ reserva.getFechaFinal().getDayOfMonth() + "/" + reserva.getFechaFinal().getMonthValue() + "/"
				+ reserva.getFechaFinal().getYear() + " - Reservado por: " + cliente.getCedula();

		return new RetirarVehiculoTO(resultado, numeroReserva);
	}

	@Override
	@Transactional
	public void retirarVehiculoReservado(Reserva reserva, Vehiculo vehiculo) {
		Reserva r = this.iReservaService.buscar(reserva.getId());
		Vehiculo v = this.iVehiculoService.buscarPorPlaca(vehiculo.getPlaca());
//		Cliente c = this.buscarCliente(cliente.getCedula());
		v.setEstado("ND");
		r.setEstado('E');
//		if(c==null ) {
//			cliente.setReservaActiva(1);
//		}else {
//			c.setReservaActiva(cliente.getReservaActiva()+1);
//		}
		this.iVehiculoService.actualizar(v);
		this.iReservaService.actualizar(r);
//		this.iClienteService.actualizarCliente(c);
	}

}
