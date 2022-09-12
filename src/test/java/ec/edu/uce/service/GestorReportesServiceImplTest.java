package ec.edu.uce.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@Transactional
@Rollback(true)
@SpringBootTest
class GestorReportesServiceImplTest {

	@Autowired
	private IGestorReportesService gestorReportesService;
	
	@Test
	void testReporteReservas() {
		assertThat(this.gestorReportesService.reporteReservas(LocalDateTime.of(2022, 05, 10, 20, 00), LocalDateTime.of(2022, 12, 30, 20, 00))).isNotNull();
	}
	
	@Test
	void testReporteReservasDos() {
		assertThat(this.gestorReportesService.reporteReservas(LocalDateTime.of(2022, 05, 10, 20, 00), LocalDateTime.of(2022, 12, 30, 20, 00))).asList();
	}

	@Test
	void testReporteClientesVIP() {
		assertThat(this.gestorReportesService.reporteClientesVIP()).asList();
	}

	@Test
	void testReporteVehiculodVIP() {
		assertThat(this.gestorReportesService.reporteVehiculodVIP("9", "2022")).isNotNull();
	
	}
	
	@Test
	void testReporteVehiculodVIPDos() {
		assertThat(this.gestorReportesService.reporteVehiculodVIP("11", "2022")).asList();
	
	}

}
