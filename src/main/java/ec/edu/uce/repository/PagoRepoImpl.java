package ec.edu.uce.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ec.edu.uce.modelo.Pago;

@Transactional
@Repository
public class PagoRepoImpl implements IPagoRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insertar(Pago pago) {
		this.entityManager.persist(pago);
	}

	@Override
	public void actualizar(Pago pago) {
		this.entityManager.merge(pago);
	}

	@Override
	public Pago buscar(Integer id) {
		return this.entityManager.find(Pago.class, id);
	}

	@Override
	public void borrar(Integer id) {
		this.entityManager.remove(this.buscar(id));
	}

}
