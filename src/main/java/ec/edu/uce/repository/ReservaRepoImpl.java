package ec.edu.uce.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ec.edu.uce.modelo.ReporteClienteVIPTO;
import ec.edu.uce.modelo.ReporteReservas;
import ec.edu.uce.modelo.ReporteVehiculosVIPTO;
import ec.edu.uce.modelo.Reserva;
import ec.edu.uce.modelo.Vehiculo;

@Transactional
@Repository
public class ReservaRepoImpl implements IReservaRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insertar(Reserva reserva) {
		this.entityManager.persist(reserva);
	}

	@Override
	public void actualizar(Reserva reserva) {
		this.entityManager.merge(reserva);
	}

	@Override
	public Reserva buscar(Integer id) {
		return this.entityManager.find(Reserva.class, id);

	}

	@Override
	public void borrar(Integer id) {
		this.entityManager.remove(this.buscar(id));
	}

	@Override
	public Reserva buscarPorNumero(String numero) {
		TypedQuery<Reserva> myQuery = this.entityManager.createQuery("SELECT r FROM Reserva r WHERE r.numero=: numero",
				Reserva.class);
		myQuery.setParameter("numero", numero);
		try {
			return myQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ReporteReservas> reporteReservas(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
		TypedQuery<ReporteReservas> myQuery = this.entityManager.createQuery(
				"SELECT NEW ec.edu.uce.modelo.ReporteReservas"
						+ "(r.id,r.numero,r.fechaInicio,r.fechaFinal,r.estado,c.apellido,c.cedula,v.placa,v.marca,v.valorPorDia) "
						+ "FROM Reserva r JOIN r.cliente c JOIN r.vehiculo v  WHERE r.fechaInicio>=:fechaInicio AND  r.fechaFinal<=:fechaFinal",
				ReporteReservas.class);
		myQuery.setParameter("fechaInicio", fechaInicio);
		myQuery.setParameter("fechaFinal", fechaFinal);
		return myQuery.getResultList();
	}

	@Override
	public List<Reserva> buscarPorVehiculo(Vehiculo vehiculo) {
		TypedQuery<Reserva> myQuery = this.entityManager
				.createQuery("SELECT r  FROM Reserva r where r.vehiculo=:vehiculo", Reserva.class);
		myQuery.setParameter("vehiculo", vehiculo);
		return myQuery.getResultList();
	}

	@Override
	public List<Reserva> todasReservas() {
		TypedQuery<Reserva> myQuery = this.entityManager.createQuery("SELECT r  FROM Reserva r ", Reserva.class);
		return myQuery.getResultList();
	}

	@Override
	public List<ReporteVehiculosVIPTO> buscarMesAnio(String mes, String anio) {
		LocalDateTime fechaInicio = LocalDateTime.of(Integer.valueOf(anio), Integer.valueOf(mes), 1, 0, 0, 0);
		LocalDateTime fechaFinal;
		if (mes != "12") {
			fechaFinal = LocalDateTime.of(Integer.valueOf(anio), Integer.valueOf(mes) + 1, 1, 0, 0, 0);
		} else {
			fechaFinal = LocalDateTime.of(Integer.valueOf(anio) + 1, 1, 1, 0, 0, 0);
		}

		TypedQuery<ReporteVehiculosVIPTO> myQuery = this.entityManager.createQuery(
				"SELECT NEW ec.edu.uce.modelo.ReporteVehiculosVIPTO(v,sum( p.valorIVA),sum(p.valorTotalAPagar) AS tot) FROM Reserva r JOIN r.vehiculo v JOIN r.pagos p WHERE r.fechaInicio >= :fechaInicio AND r.fechaFinal < :fechaFinal  GROUP BY v ORDER BY tot DESC",
				ReporteVehiculosVIPTO.class).setParameter("fechaInicio", fechaInicio)
				.setParameter("fechaFinal", fechaFinal);

		return myQuery.getResultList();
	}

	@Override
	public List<ReporteClienteVIPTO> busquedaClientesVip() {
		TypedQuery<ReporteClienteVIPTO> myQuery = this.entityManager.createQuery(
				"SELECT NEW ec.edu.uce.modelo.ReporteClienteVIPTO(c,sum( p.valorIVA),sum(p.valorTotalAPagar) AS tot)"
						+ " FROM Reserva r JOIN r.cliente c JOIN r.pagos p GROUP BY c ORDER BY tot DESC",
				ReporteClienteVIPTO.class);

		return myQuery.getResultList();
	}
}
