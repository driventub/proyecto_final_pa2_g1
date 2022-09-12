package ec.edu.uce.modelo;

import java.math.BigDecimal;

public class ReporteClienteVIPTO {

	private Cliente cliente;

	private BigDecimal valorIVA;

	private BigDecimal valorTotalAPagar;

	public ReporteClienteVIPTO() {
	}

	public ReporteClienteVIPTO(Cliente cliente, BigDecimal valorIVA, BigDecimal valorTotalAPagar) {
		this.cliente = cliente;
		this.valorIVA = valorIVA;
		this.valorTotalAPagar = valorTotalAPagar;
	}

	// gets and set
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

}
