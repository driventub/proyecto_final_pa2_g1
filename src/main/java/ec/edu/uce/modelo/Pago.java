package ec.edu.uce.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "pago")
public class Pago {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pago")
	@SequenceGenerator(name = "seq_pago", sequenceName = "seq_pago", allocationSize = 1)
	@Column(name = "pago_id")
	private Integer id;

	@Column(name = "pago_valor_subtotal")
	private BigDecimal valorSubTotal;

	@Column(name = "pago_valor_IVA")
	private BigDecimal valorIVA;

	@Column(name = "pago_valor_total_pagar")
	private BigDecimal valorTotalAPagar;

	@Column(name = "pago_fecha_cobro")
	// @DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime fechaCobro;

	@Column(name = "pago_tarjeta")
	private String tarjeta;

	@OneToOne(mappedBy = "pagos", cascade = CascadeType.ALL)
	private Reserva pagoReserva;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValorSubTotal() {
		return valorSubTotal;
	}

	public void setValorSubTotal(BigDecimal valorSubTotal) {
		this.valorSubTotal = valorSubTotal;
	}

	public BigDecimal getValorIVA() {
		return valorIVA;
	}

	public void setValorIVA(BigDecimal valorIVA) {
		this.valorIVA = valorIVA;
	}

	public BigDecimal getValorTotalAPagar() {
		return valorTotalAPagar;
	}

	public void setValorTotalAPagar(BigDecimal valorTotalAPagar) {
		this.valorTotalAPagar = valorTotalAPagar;
	}

	public LocalDateTime getFechaCobro() {
		return fechaCobro;
	}

	public void setFechaCobro(LocalDateTime fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Reserva getPagoReserva() {
		return pagoReserva;
	}

	public void setPagoReserva(Reserva pagoReserva) {
		this.pagoReserva = pagoReserva;
	}
	
	

}
