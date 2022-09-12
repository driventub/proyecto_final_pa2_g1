package ec.edu.uce.modelo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ReporteFechasTO {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaFinal;

	public ReporteFechasTO() {
	}

	public ReporteFechasTO(LocalDate fechaInicio, LocalDate fechaFinal) {
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
	}

	// get and sets
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(LocalDate fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

}
