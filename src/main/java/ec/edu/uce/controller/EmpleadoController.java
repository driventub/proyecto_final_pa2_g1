package ec.edu.uce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ec.edu.uce.modelo.Cliente;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.ReservarVehiculoTO;
import ec.edu.uce.modelo.SinReservaTO;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.service.IClienteService;
import ec.edu.uce.service.IGestorClienteService;
import ec.edu.uce.service.IGestorEmpleadoService;
import ec.edu.uce.service.IReservaService;
import ec.edu.uce.service.IVehiculoService;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

	private static Logger LOG =  LogManager.getLogger(ClienteController.class);

	@Autowired
	private IGestorEmpleadoService gestorEmpleadoService;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IVehiculoService vehiculoService;

	@Autowired
	private IReservaService reservaService;

	@Autowired
	private IGestorClienteService gestorClienteService;

	@GetMapping("/registro")
	public String registroClienteE(Cliente cliente) {
		return "e_registro";
	}

	@GetMapping("/insertar")
	public String insertarClienteE(Cliente cliente, BindingResult result, Model modelo,
			RedirectAttributes redirectAttrs) {
		this.gestorEmpleadoService.registrarCliente(cliente);
		// return "c_registro_valido";
		LOG.info("registrando tipo empleado");
		return "redirect:/empleados/todos?exito";
	}

	@GetMapping("/editar/{clieCedula}")
	public String editarCliente(@PathVariable("clieCedula") String cedula, Cliente cliente, Model modelo) {
		Cliente clie = this.clienteService.buscarClientePorCedula(cedula);
		LOG.info(clie.toString());
		modelo.addAttribute("clie", clie);
		return "clieActualizaE";
	}

	@PutMapping("/actualizar/{clieCedula}")
	public String actualizarCliente(@PathVariable("clieCedula") String cedula, Cliente clie,
			RedirectAttributes redirectAttrs) {
		Cliente cliente = this.clienteService.buscarClientePorCedula(clie.getCedula());
		clie.setId(cliente.getId());
		clie.setTipoRegistro(cliente.getTipoRegistro());
		clie.setReservaActiva(cliente.getReservaActiva());
		this.clienteService.actualizarCliente(clie);
		redirectAttrs.addFlashAttribute("mensaje", "Cliente cedula: " + clie.getCedula() + " editado correctamente")
				.addFlashAttribute("clase", "success");
		return "redirect:/empleados/todos";
	}

	@GetMapping("/eliminar/{clieCedula}")
	public String eliminarCliente(@PathVariable("clieCedula") String cedula, Model modelo,
			RedirectAttributes redirectAttrs) {
		Cliente cliente = this.clienteService.buscarClientePorCedula(cedula);
		Boolean validar = this.clienteService.verificarReserva(cliente.getId());

		if (validar == true) {

			this.clienteService.borrarClientePorId(cliente.getId());

		} else {
			// modelo.addAttribute("validar", false);
			redirectAttrs.addFlashAttribute("mensaje2", "No se puede eliminar, tiene reservas activas")
					.addFlashAttribute("clase2", "danger");
		}

		return "redirect:/empleados/todos";
	}

	@GetMapping("/ver/{cedula}")
	public String verDatosCliente(@PathVariable("cedula") String cedula, Model modelo) {
		Cliente cliente = this.clienteService.buscarClientePorCedula(cedula);
		modelo.addAttribute("cliente", cliente);
		return "datosCliente";
	}

	@GetMapping("/todos")
	public String listarClientes(Model modelo, @Param("apellido") String apellido) {
		List<Cliente> clientes2 = this.clienteService.listarClientes();

		List<Cliente> clientes = this.clienteService.listarClientesPorApellido(apellido);
		modelo.addAttribute("clientes", clientes);
		modelo.addAttribute("apellido", apellido);
		LOG.info("apellido: " + apellido);
		if (apellido == null) {
			modelo.addAttribute("clientes", clientes2);
		}

		return "todosClientesE";
	}

	// Vehiculos

	@GetMapping("/registroVehi")
	public String vistaRegistroVehiculos(Vehiculo vehiculo) {
		return "vehi_ingreso_e";
	}

	@PostMapping("/insertarVehiculo")
	public String insertarVehiculo(Vehiculo vehiculo) {

		this.gestorEmpleadoService.ingresarVehiculo(vehiculo);

		return "redirect:/empleados/registroVehi?exito";
	}

	@GetMapping("/vehiTodos")
	public String listarVehiculos(Model modelo, @Param("marca") String marca) {
		List<Vehiculo> vehiTodos = this.vehiculoService.buscarTodos();

		List<Vehiculo> vehiMarca = this.vehiculoService.buscarMarca(marca);
		modelo.addAttribute("vehiculos", vehiMarca);
		modelo.addAttribute("marca", marca);
		LOG.info("marca: " + marca);
		if (marca == null) {
			modelo.addAttribute("vehiculos", vehiTodos);
		}
		return "vehi_todos";
	}

	@GetMapping("/verVehi/{placa}")
	public String verVehiculo(@PathVariable("placa") String placa, Model modelo) {
		Vehiculo vehiculo = this.vehiculoService.buscarPorPlaca(placa);
		modelo.addAttribute("vehiculo", vehiculo);
		return "vehi_ver";
	}

	@GetMapping("/editarVehi/{placa}")
	public String editarVehiculo(@PathVariable("placa") String placa, Model modelo) {
		Vehiculo vehiculo = this.vehiculoService.buscarPorPlaca(placa);
		LOG.info(vehiculo.toString());
		modelo.addAttribute("vehiculo", vehiculo);
		return "vehi_actualiza";
	}

	@PutMapping("/actualizarVehi/{placa}")
	public String actualizarVehiculo(@PathVariable("placa") String placa, RedirectAttributes redirectAttrs,
			Vehiculo vehiculo) {

		Vehiculo vehi = this.vehiculoService.buscarPorPlaca(placa);

		vehiculo.setId(vehi.getId());

		this.vehiculoService.actualizar(vehiculo);
		redirectAttrs.addFlashAttribute("mensaje", "Vehiculo placa: " + vehiculo.getPlaca() + " editado correctamente")
				.addFlashAttribute("clase", "success");
		return "redirect:/empleados/vehiTodos";
	}

	@GetMapping("/eliminarVehi/{placa}")
	public String eliminarVehiculo(@PathVariable("placa") String placa, Model modelo,
			RedirectAttributes redirectAttrs) {
		Vehiculo vehiculo = this.vehiculoService.buscarPorPlaca(placa);
		Boolean validar = this.vehiculoService.verificarVehiculo(vehiculo.getId());
		Boolean validarRes = this.vehiculoService.verificarReserva(placa);
		if (validar == true && validarRes == true ) {

			this.vehiculoService.borrar(vehiculo.getId());

		} else {
			// modelo.addAttribute("validar", false);
			redirectAttrs.addFlashAttribute("mensaje2", "No se puede eliminar, tiene reservas activas")
					.addFlashAttribute("clase2", "danger");
		}

		return "redirect:/empleados/vehiTodos";
	}

	/***********************
	 * retiro del vehiculo con reserva
	 ****************************/
	// 2.e

	@GetMapping("/buscarReserva")
	public String buscarVehiculo(Reserva reserva, Model model) {
		return "obtenerNumeroReserva";

	}

	@GetMapping("/reservar")
	public String obtenerReserva(Reserva reserva, Model model, RedirectAttributes redirectAttrs) {
		Reserva reser = this.reservaService.buscarPorNumero(reserva.getNumero());
		Vehiculo vehiculo = this.vehiculoService.buscar(reser.getVehiculo().getId());
		
			model.addAttribute("reserva", reser);
			model.addAttribute("vehiculo", vehiculo);
			return "reserva";
	}

	@PutMapping("/retirarVehiculo")
	public String retirarVehiculo(Reserva reserva, Vehiculo vehiculo, Model model, RedirectAttributes redirectAttrs) {

		Reserva rese = this.reservaService.buscar(reserva.getId());
		Vehiculo vehi = this.vehiculoService.buscarPorPlaca(vehiculo.getPlaca());
		if (rese.getEstado() == 'G' && vehi.getEstado().equals("D")) {
			this.gestorEmpleadoService.retirarVehiculoReservado(reserva, vehiculo);
			model.addAttribute("reserva", reserva);
			redirectAttrs.addFlashAttribute("mensaje", "Vehiculo retirado con exito!!!");
			redirectAttrs.addFlashAttribute("clase", "success");
		} else {
			redirectAttrs.addFlashAttribute("mensaje", "No se puede retirar!!! Vehiculo ya retirado");
			redirectAttrs.addFlashAttribute("clase", "danger");
		}

		return "redirect:/empleados/buscarReserva";
	}

	/***********************
	 * retiro del vehiculo sin reserva
	 ****************************/
	// 2.f
	@GetMapping("/retirar/sinReserva")
	private String retirarSinReserva(SinReservaTO sinReservaTO, ReservarVehiculoTO reservarVehiculoTO, Model modelo) {

		modelo.addAttribute("visible1", false);
		modelo.addAttribute("visible2", false);
		modelo.addAttribute("visible3", false);
		return "marcaModelo";
	}

	@PostMapping("/disponiblidad")
	public String buscarVehiculosT(@RequestParam(name = "marca") String idMarca,
			@RequestParam(name = "modelo") String idModelo, Model modelo, SinReservaTO sinReservaTO,
			ReservarVehiculoTO reservarVehiculoTO) {

		modelo.addAttribute("listVehiculos", this.gestorClienteService.buscarVehiculosDisponibles(idMarca, idModelo));
		modelo.addAttribute("visible1", true);
		modelo.addAttribute("visible2", true);
		modelo.addAttribute("visible3", false);
		return "marcaModelo";
	}

	@PostMapping("/sinReserva/buscarReserva")
	public String insertarReserva(ReservarVehiculoTO reservarVehiculoTO, SinReservaTO sinReservaTO,
			BindingResult result, Model modelo, RedirectAttributes redirectAttributes) {

		if (this.gestorClienteService.verificarDisponibilidad(reservarVehiculoTO)) {
			redirectAttributes.addFlashAttribute("mensaje", "Vehiculo disponible");
			LOG.info(this.gestorClienteService.generarPago(reservarVehiculoTO.getPlaca(),
			reservarVehiculoTO.getFechaInicio(), reservarVehiculoTO.getFechaFinal()).getValorTotalAPagar());
			reservarVehiculoTO.setValorTotalAPagar(this.gestorClienteService.generarPago(reservarVehiculoTO.getPlaca(),
					reservarVehiculoTO.getFechaInicio(), reservarVehiculoTO.getFechaFinal()).getValorTotalAPagar());

			modelo.addAttribute("reservarVehiculoTO", reservarVehiculoTO);
			modelo.addAttribute("visible1", true);
			modelo.addAttribute("visible2", true);
			modelo.addAttribute("visible3", true);
			return "marcaModelo";
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "Vehiculo no disponible o Fechas incorrectas");
			return "redirect:/empleados/retirar/sinReserva";
		}

	}

	@PostMapping("/insertarPago")
	public String insertarPago(ReservarVehiculoTO reservarVehiculoTO, SinReservaTO sinReservaTO, BindingResult result,
			Model modelo, RedirectAttributes redirectAttributes) {

//		LOG.info(reservarVehiculoTO.getFechaInicio());
//		LOG.info(reservarVehiculoTO.getFechaFinal());
		Reserva reserva = this.gestorClienteService.crearReserva(reservarVehiculoTO);

		this.gestorEmpleadoService.retirarVehiculoReservado(reserva, reserva.getVehiculo());
		
		redirectAttributes.addFlashAttribute("mensaje", "Reservacion Creada");
		return "redirect:/empleados/retirar/sinReserva";
	}


	
}
