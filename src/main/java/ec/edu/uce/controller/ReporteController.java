package ec.edu.uce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ec.edu.uce.modelo.MesAnioTo;
import ec.edu.uce.modelo.ReporteClienteVIPTO;
import ec.edu.uce.modelo.ReporteReservas;
import ec.edu.uce.modelo.ReporteVehiculosVIPTO;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.service.IGestorReportesService;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

	@Autowired
	private IGestorReportesService gestReportesService;
	
/////////////////////////////////////////////////////////////////////////////////////	
	
	@GetMapping("/reporteReserva")
	public String obtenerPaginaReporte(Reserva reserva) {

		return "ingresaFechas";

	}

	@GetMapping("/reporteReservado")
	public String generarReporte(Reserva reserva, BindingResult result, Model modelo) {

		List<ReporteReservas> reservas = this.gestReportesService.reporteReservas(reserva.getFechaInicio(), reserva.getFechaFinal());

		modelo.addAttribute("reservas", reservas);

		return "reporteReservas";
	}
	
/////////////////////////////////////////////////////////////////////////////////////

	
	@GetMapping("/reporteClientesVip")
	public String generarReporteCliVip(Model modelo) {

		List<ReporteClienteVIPTO> clientes = this.gestReportesService.reporteClientesVIP();

		modelo.addAttribute("cliente", clientes);

		return "reporteCli";
	}
	
/////////////////////////////////////////////////////////////////////////////////////


@GetMapping("/reporteVehiculoVip")
public String obtenerPaginaDatosReporteMesAnio(MesAnioTo mesAnioTO , Model modelo) {
	return "reporteVehiculoMesAnio";
}

@GetMapping("/vehiculoVip")
public String obtenerReporteMesAnio(MesAnioTo mesAnioTO ,
		Model modelo) {
	List<ReporteVehiculosVIPTO> listaReporte = this.gestReportesService.reporteVehiculodVIP(mesAnioTO.getMes(), mesAnioTO.getAnio());
	modelo.addAttribute("listaVIP", listaReporte);
	return "reporteVehiculoVIP";
}
	
}
