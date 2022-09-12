package ec.edu.uce.repository;

import ec.edu.uce.modelo.Pago;

public interface IPagoRepo {

	void insertar(Pago pago);

	void actualizar(Pago pago);

	Pago buscar(Integer id);

	void borrar(Integer id);
}
