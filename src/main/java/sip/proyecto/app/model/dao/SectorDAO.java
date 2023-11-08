package sip.proyecto.app.model.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sip.proyecto.app.model.entity.Sector;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SectorDAO implements ISectorDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Sector findById(long id) {
		Session currentSession = this.entityManager.unwrap(Session.class);
		Sector sector = currentSession.get(Sector.class, id);
		return sector;
	}

	@Override
	@Transactional
	public List<Sector> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		String query = "FROM Sector";
		List<Sector> list = currentSession.createQuery(query, Sector.class).getResultList();
		if(list.isEmpty())
			throw new Error("No hay sectores cargados");
		return list;
	}

	@Override
	@Transactional
	public void save(Sector sector) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.persist(sector);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Sector sector = this.findById(id);
		Query updateInqulinos = currentSession
				.createQuery("update Item set currentLocation = null where currentLocation.sector.id=:id");
		updateInqulinos.setParameter("id", id);
		updateInqulinos.executeUpdate();
		currentSession.remove(sector);
	}


}
