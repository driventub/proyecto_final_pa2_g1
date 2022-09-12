package ec.edu.uce.modelo;

public class RetirarVehiculoTO {

	private String dato;

	private String numero;

	public RetirarVehiculoTO() {
	}

	public RetirarVehiculoTO(String dato, String numero) {
		super();
		this.dato = dato;
		this.numero = numero;
	}

//gets and sets
	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
