package ec.edu.uce.modelo;

import java.math.BigDecimal;

public class ReporteVehiculosVIPTO {

	private Vehiculo vehiculo;

	private BigDecimal valorIVA;

	private BigDecimal valorTotalAPagar;

	public ReporteVehiculosVIPTO() {
	}

	public ReporteVehiculosVIPTO(Vehiculo vehiculo, BigDecimal valorIVA, BigDecimal valorTotalAPagar) {
		this.vehiculo = vehiculo;
		this.valorIVA = valorIVA;
		this.valorTotalAPagar = valorTotalAPagar;
	}

	// gets and set
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
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

	@Override
	public String toString() {
		return "ReporteVehiculosVIPTO [vehiculo=" + vehiculo + ", valorIVA=" + valorIVA + ", valorTotalAPagar="
				+ valorTotalAPagar + "]";
	}

}
