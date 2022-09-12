package ec.edu.uce.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class ReservarVehiculoTO {

	private String placa;

	private String cedula;

	//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm")
	private LocalDateTime fechaInicio;

	//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@DateTimeFormat(pattern = "yyyy-MM-dd\'T\'HH:mm")
	private LocalDateTime fechaFinal;

	private BigDecimal valorTotalAPagar;

	private String tarjeta;

	public ReservarVehiculoTO() {
	}

	public ReservarVehiculoTO(String placa, String cedula, LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
		this.placa = placa;
		this.cedula = cedula;
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
	}

	// gets and sets
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(LocalDateTime fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public BigDecimal getValorTotalAPagar() {
		return valorTotalAPagar;
	}

	public void setValorTotalAPagar(BigDecimal valorTotalAPagar) {
		this.valorTotalAPagar = valorTotalAPagar;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	@Override
	public String toString() {
		return "ReservarVehiculoTO [placa=" + placa + ", cedula=" + cedula + ", fechaInicio=" + fechaInicio
				+ ", fechaFinal=" + fechaFinal + ", valorTotalAPagar=" + valorTotalAPagar + ", tarjeta=" + tarjeta
				+ "]";
	}

	
	
	
}
