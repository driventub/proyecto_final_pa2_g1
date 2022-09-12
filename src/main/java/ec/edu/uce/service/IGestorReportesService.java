package ec.edu.uce.service;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.uce.modelo.ReporteClienteVIPTO;
import ec.edu.uce.modelo.ReporteReservas;
import ec.edu.uce.modelo.ReporteVehiculosVIPTO;

public interface IGestorReportesService {

	List<ReporteReservas> reporteReservas(LocalDateTime fechaInicio, LocalDateTime fechaFinal);

	List<ReporteClienteVIPTO> reporteClientesVIP();

	List<ReporteVehiculosVIPTO> reporteVehiculodVIP(String mes, String anio);
}
