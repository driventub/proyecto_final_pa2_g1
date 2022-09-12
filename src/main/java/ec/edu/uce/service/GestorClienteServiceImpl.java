package ec.edu.uce.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Cliente;
import ec.edu.uce.modelo.Pago;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.ReservarVehiculoTO;
import ec.edu.uce.modelo.Vehiculo;

@Service
public class GestorClienteServiceImpl implements IGestorClienteService , IIVAService {

	private static Logger LOG =  LogManager.getLogger(GestorClienteServiceImpl.class);

	@Autowired
	private IClienteService iClienteService;

	@Autowired
	private IVehiculoService iVehiculoService;

	@Autowired
	private IReservaService iReservaService;

	

	Function<Vehiculo, String> generarVehiculo = vehiculo -> {

		String estado = (vehiculo.getEstado() == "D") ? "Disponible" : "No Disponible";
		String resultado = "Placa: " + vehiculo.getPlaca() + " - Modelo: " + vehiculo.getModelo() + " - Estado: "
				+ estado + " - Valor por dia: $" + vehiculo.getValorPorDia();
		return resultado;
	};

	@Override
	@Transactional(value=TxType.NOT_SUPPORTED)
	public List<Vehiculo> buscarVehiculosDisponibles(String marca, String modelo) {
		List<Vehiculo> lista = this.iVehiculoService.buscarMarcaModelo(marca, modelo);
		List<Vehiculo> listaF = lista.stream().filter(valor -> valor.getEstado().equals("D")).collect(Collectors.toList());
		// lista.forEach(vehi -> LOG.info(vehi));
		return listaF;
	}


	@Override
	@Transactional(value=TxType.REQUIRED)
	public Reserva reservarVehiculo(String placa, String cedulaCliente, LocalDateTime fechaInicio,
			LocalDateTime fechaFinal, String numeroTarjeta) {

		
		
		Vehiculo vehiculo = this.iVehiculoService.buscarPorPlaca(placa);
		Cliente cliente = this.iClienteService.buscarClientePorCedula(cedulaCliente);
//		ReservarVehiculoTO r=new ReservarVehiculoTO();
//		r.setCedula(cedulaCliente);
//		r.setPlaca(placa);
//		r.setFechaFinal(fechaFinal);
//		r.setFechaInicio(fechaInicio);
//		r.setTarjeta(numeroTarjeta);
//		
//		
//		boolean dispo = this.verificarDisponibilidad(null);
		
		Reserva reserva = new Reserva();
		reserva.setCliente(cliente);
		reserva.setEstado('G');
		reserva.setFechaFinal(fechaFinal);
		reserva.setFechaInicio(fechaInicio);
		reserva.setNumero(cliente.getApellido() + "-" + vehiculo.getPlaca() + "-" + fechaInicio.getYear() + "-"
				+ fechaInicio.getMonthValue() + "-" + fechaInicio.getDayOfMonth());

		Pago pago = this.generarPago(placa, fechaInicio, fechaFinal);
		pago.setFechaCobro(LocalDateTime.now());
		pago.setPagoReserva(reserva);
		pago.setTarjeta(numeroTarjeta);

		reserva.setPagos(pago);
		reserva.setVehiculo(vehiculo);
		
		cliente.setReservaActiva(cliente.getReservaActiva()+1);
		
		this.iClienteService.actualizarCliente(cliente);
		this.iReservaService.insertar(reserva);
		
		return reserva;
	
		
	}

	@Override
	@Transactional(value=TxType.REQUIRED)
	public void registrarCliente(Cliente cliente) {
		cliente.setTipoRegistro('C');
		cliente.setReservaActiva(0);
		this.iClienteService.insertarCliente(cliente);
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public boolean verificarDisponibilidad(ReservarVehiculoTO reservarVehiculoTO) {

		if (reservarVehiculoTO.getFechaInicio().isBefore(reservarVehiculoTO.getFechaFinal())
				&& reservarVehiculoTO.getFechaInicio().isAfter(LocalDateTime.now())) {

			Vehiculo vehiculo = this.iVehiculoService.buscarPorPlaca(reservarVehiculoTO.getPlaca());
			LocalDateTime fechaInicio = reservarVehiculoTO.getFechaInicio();

			List<Reserva> lista = this.iReservaService.buscarPorVehiculo(vehiculo);

			if (lista.stream().filter(v -> fechaInicio.isBefore(v.getFechaFinal())).count() > 0) {
				LOG.info(lista.stream().filter(v -> fechaInicio.isAfter(v.getFechaFinal())).count());
				return false;
			}
			return true;
		}

		return false;
//		Vehiculo vehiculo = this.iVehiculoService.buscarPorPlaca(reservarVehiculoTO.getPlaca());
//		LocalDateTime fechaInicio = reservarVehiculoTO.getFechaInicio();
//
//		List<Reserva> lista = this.iReservaService.buscarPorVehiculo(vehiculo);
//
//		if (lista.stream().filter(v -> fechaInicio.isBefore(v.getFechaFinal())).count() > 0) {
//			LOG.info(lista.stream().filter(v -> fechaInicio.isAfter(v.getFechaFinal())).count());
//			return false;
//		}
//		return true;
	}

	@Override
	@Transactional
	public Pago generarPago(String placa, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {

		Vehiculo vehiculo = this.iVehiculoService.buscarPorPlaca(placa);

		long diasAlquiler = Duration.between(fechaInicio, fechaFinal).toDays();
		BigDecimal valorSubTotal = vehiculo.getValorPorDia().multiply((new BigDecimal(diasAlquiler)));
		BigDecimal valorIVA = IVA.multiply(valorSubTotal);
		BigDecimal valorTotalPagar = valorSubTotal.add(valorIVA);

		Pago pago = new Pago();
		pago.setFechaCobro(LocalDateTime.now());
		pago.setValorIVA(valorIVA);
		pago.setValorSubTotal(valorSubTotal);
		pago.setValorTotalAPagar(valorTotalPagar);

		return pago;
	}

	@Override
	@Transactional
	public Reserva crearReserva(ReservarVehiculoTO reservarVehiculoTO) {
		return this.reservarVehiculo(reservarVehiculoTO.getPlaca(), reservarVehiculoTO.getCedula(),
				reservarVehiculoTO.getFechaInicio(), reservarVehiculoTO.getFechaFinal(),
				reservarVehiculoTO.getTarjeta());

	}

	


}
