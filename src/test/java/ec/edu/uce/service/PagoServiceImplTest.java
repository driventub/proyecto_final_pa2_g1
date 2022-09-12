package ec.edu.uce.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import ec.edu.uce.modelo.Pago;


@SpringBootTest
@Transactional
@Rollback(true)
class PagoServiceImplTest {

	@Autowired
	private IPagoService pagoService;
	
	
	@Test
	void testInsertar() {
		
		Pago pago=new Pago();
		pago.setFechaCobro(LocalDateTime.now());
		pago.setTarjeta("123");
		pago.setValorIVA(new BigDecimal(0.12));
		pago.setValorSubTotal(new BigDecimal(120));
		pago.setValorTotalAPagar(pago.getValorIVA().multiply(pago.getValorSubTotal()));
		this.pagoService.insertar(pago);
		assertThat(this.pagoService.buscar(pago.getId())).isNotNull();
	}

	@Test
	void testActualizar() {
		Pago p=this.pagoService.buscar(1);
		p.setTarjeta("Prueba");
		//p.setId(1);
		this.pagoService.actualizar(p);
		assertTrue(this.pagoService.buscar(p.getId()).getTarjeta().contains("P"));
		
	}

	@Test
	void testBuscar() {
		assertNotNull(this.pagoService.buscar(1));
	}

	@Test
	void testBorrar() {
		Pago p = this.pagoService.buscar(1);
		this.pagoService.borrar(p.getId());
		assertThat(this.pagoService.buscar(1)).isNull();
	}

}
