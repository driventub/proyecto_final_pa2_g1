package ec.edu.uce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Pago;
import ec.edu.uce.repository.IPagoRepo;

@Service
public class PagoServiceImpl implements IPagoService {

	@Autowired
	private IPagoRepo iPagoRepo;

	@Override
	public void insertar(Pago pago) {
		this.iPagoRepo.insertar(pago);
	}

	@Override
	public void actualizar(Pago pago) {
		this.iPagoRepo.actualizar(pago);
	}

	@Override
	public Pago buscar(Integer id) {
		return this.iPagoRepo.buscar(id);
	}

	@Override
	public void borrar(Integer id) {
		this.iPagoRepo.borrar(id);
	}

}
