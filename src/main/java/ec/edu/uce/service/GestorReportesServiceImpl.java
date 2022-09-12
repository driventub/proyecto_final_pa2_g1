package ec.edu.uce.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.ReporteClienteVIPTO;
import ec.edu.uce.modelo.ReporteReservas;
import ec.edu.uce.modelo.ReporteVehiculosVIPTO;

@Service
public class GestorReportesServiceImpl implements IGestorReportesService {

	@Autowired
	private IReservaService iReservaService;



	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<ReporteReservas> reporteReservas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
		return this.iReservaService.reporteReservas(fechaInicio, fechaFinal);
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<ReporteClienteVIPTO> reporteClientesVIP() {
		
		return this.iReservaService.busquedaClientesVip();
	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public List<ReporteVehiculosVIPTO> reporteVehiculodVIP(String mes, String anio) {
		Integer mesNuevo = Integer.valueOf(mes);
        if (mesNuevo >= 1 && mesNuevo <= 9) {
            mes = "0" + mes;
            
        } 
		return this.iReservaService.buscarMesAnio(mes, anio);
	}

}
