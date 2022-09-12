package ec.edu.uce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import ec.edu.uce.modelo.ReservarVehiculoTO;
import ec.edu.uce.modelo.Vehiculo;
import ec.edu.uce.service.IClienteService;
import ec.edu.uce.service.IGestorClienteService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	private static Logger LOG =  LogManager.getLogger(ClienteController.class);

    @Autowired
    private IGestorClienteService clienteeGestorServic;
      
    @Autowired
    private IClienteService clienteService;

	/////////////////////////////////////////////////////////////////////////////////////
    // c.a
	/////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/vehiculoBuscar")
	public String obtenerVehiculo(Vehiculo vehiculo) {
		return "v_buscar_marca";
	}

    @GetMapping("/buscar/marcaModelo")
	public String buscarVehiculo(Vehiculo vehiculo, BindingResult result, Model model,
			RedirectAttributes redirectAttrs) {

		List<Vehiculo> listaVehiculo = this.clienteeGestorServic.buscarVehiculosDisponibles( vehiculo.getMarca(),vehiculo.getModelo());
        model.addAttribute("listaVehiculo", listaVehiculo);
		return "v_m_marca";
	}

	/////////////////////////////////////////////////////////////////////////////////////
    //c.b
	/////////////////////////////////////////////////////////////////////////////////////
    
    @GetMapping("/disponiblesBuscar")
    public String vistaDisponibilidad(ReservarVehiculoTO reservarVehiculoTO) {
    	
    	return "v_b_disponibles";
    }
    
    @PostMapping("/disponibles")
    public String vistaBuscarDisponibles(ReservarVehiculoTO reservarVehiculoTO, Model modelo,RedirectAttributes redirectAttributes) {
    	
    	if (this.clienteeGestorServic.verificarDisponibilidad(reservarVehiculoTO)) {
			redirectAttributes.addFlashAttribute("mensaje", "Vehiculo disponible");	
			reservarVehiculoTO.setValorTotalAPagar(this.clienteeGestorServic.generarPago(reservarVehiculoTO.getPlaca(),
					reservarVehiculoTO.getFechaInicio(), reservarVehiculoTO.getFechaFinal()).getValorTotalAPagar());

			modelo.addAttribute("reservarVehiculoTO", reservarVehiculoTO);
			modelo.addAttribute("placa", reservarVehiculoTO.getPlaca());
			
			
			return "vehi_pago_c";
		} else {
			redirectAttributes.addFlashAttribute("mensaje", "Vehiculo no disponible o Fechas incorrectas");
			return "redirect:/clientes/disponiblesBuscar?fallo";
		}
    
    }
    
    
    
    @PostMapping("/insertarPago")
	public String insertarPago(ReservarVehiculoTO reservarVehiculoTO, BindingResult result, Model modelo,
			RedirectAttributes redirectAttributes) {

    	LOG.info("metodo pago");
    	
		LOG.info(reservarVehiculoTO.getFechaInicio());
		LOG.info(reservarVehiculoTO.getFechaFinal());
		LOG.info(reservarVehiculoTO.getPlaca());

		this.clienteeGestorServic.crearReserva(reservarVehiculoTO);
//		redirectAttributes.addFlashAttribute("mensaje", "Reservacion Creada");
		return "redirect:/clientes/disponiblesBuscar?exito";
	}
    
    
    /////////////////////////////////////////////////////////////////////////////////////
    // c.c
	/////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/registro")	
	public String registroCliente(Cliente cliente) {
		return "c_registro";
	}

    @GetMapping("/insertar")
	public String insertarClienteC(Cliente cliente, BindingResult result, Model modelo,
			RedirectAttributes redirectAttrs) {
    		this.clienteeGestorServic.registrarCliente(cliente);
		//return "c_registro_valido";
		return "redirect:/clientes/registro?exito";
	}

    @GetMapping("/clieEdita")
    public String paginaActualizacionCliente(Cliente cliente) {
    	return "clie-cedula";
    }
    
    
    @GetMapping("/editar")
    public String editarCliente(@RequestParam("cedula") String cedula, Cliente cliente,Model modelo,RedirectAttributes redirectAttrs) {
    	if(this.clienteService.validarTipoRegistroC(cedula)) {
    		Cliente clie = this.clienteService.buscarClientePorCedula(cedula);
        	LOG.info(clie.toString());
        	modelo.addAttribute("clie", clie);
        	return "clieActualiza";
    	}else {
    		redirectAttrs
            .addFlashAttribute("mensaje2", "Cedula incorrecta")
            .addFlashAttribute("clase2", "danger");
    		
    		return "redirect:/clientes/clieEdita";
    	}
    	
    }
    
    @PutMapping("/actualizar/{clieCedula}")
    public String actualizarCliente(@PathVariable("clieCedula")String cedula,Cliente cliente,RedirectAttributes redirectAttrs) {
    	Cliente clienteN = this.clienteService.buscarClientePorCedula(cliente.getCedula());
    	cliente.setId(clienteN.getId());
    	cliente.setTipoRegistro(clienteN.getTipoRegistro());
    	cliente.setReservaActiva(clienteN.getReservaActiva());
    	this.clienteService.actualizarCliente(cliente);
    	redirectAttrs
        .addFlashAttribute("mensaje", "Cliente cedula: "+cliente.getCedula()+" editado correctamente")
        .addFlashAttribute("clase", "success");
    	return "redirect:/clientes/clieEdita";
    }
    
    @GetMapping("/todos")
   public String listarClientes(Model modelo) {
	   List<Cliente> clientes = this.clienteService.listarClientes();
	   modelo.addAttribute("clientes", clientes);
    	return "todosClientes";
   }
}
