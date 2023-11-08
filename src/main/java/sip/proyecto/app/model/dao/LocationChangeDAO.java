package sip.proyecto.app.model.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sip.proyecto.app.model.entity.LocationChange;

import org.hibernate.Session;

import org.springframework.stereotype.Repository;

@Repository
public class LocationChangeDAO implements ILocationChangeDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public LocationChange findById(long id) {
		Session currentSession = this.entityManager.unwrap(Session.class);
		LocationChange location = currentSession.get(LocationChange.class, id);
		if (location!= null)
				return location;
		throw new Error("No se encontro la location con id: "+id);
	}

	@Override
	@Transactional
	public List<LocationChange> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		String query = "FROM LocationChange";
		List<LocationChange> list = currentSession.createQuery(query, LocationChange.class).getResultList();
		if(list.isEmpty())
			throw new Error("No hay locations cargadas");
		return list;
	}

	@Override
	@Transactional
	public void save(LocationChange location) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.persist(location);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		Session currentSession = entityManager.unwrap(Session.class);
		LocationChange location = this.findById(id);
		currentSession.remove(location);
	}


}
