package ec.edu.uce.service;

import ec.edu.uce.modelo.Pago;

public interface IPagoService {

	void insertar(Pago pago);

	void actualizar(Pago pago);

	Pago buscar(Integer id);

	void borrar(Integer id);
}
