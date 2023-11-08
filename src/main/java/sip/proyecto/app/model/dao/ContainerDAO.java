package sip.proyecto.app.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sip.proyecto.app.model.entity.Container;


@Repository
public class ContainerDAO implements IContainerDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Container findById(long id) {
		Session currentSession = this.entityManager.unwrap(Session.class);
		Container cont = currentSession.get(Container.class, id);
		if (cont != null)
			return cont;
		throw new Error("No se encontro el contenedor");
	}

	@Override
	@Transactional
	public List<Container> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		String query = "FROM Container";
		List<Container> list = currentSession.createQuery(query, Container.class).getResultList();
		if (list.isEmpty())
			throw new Error("No hay contenedores cargados");
		return list;
	}

	@Override
	@Transactional
	public void save(Container container) {
		Session currentSession = entityManager.unwrap(Session.class);
		try {
			currentSession.persist(container);
			
		}catch(Throwable e) {throw new Error(e.getMessage());}
		
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query updateInqulinos = currentSession
				.createQuery("update Item set currentLocation = null where currentLocation.id=:id");
		updateInqulinos.setParameter("id", id);
		updateInqulinos.executeUpdate();
		Container container = this.findById(id);
		currentSession.remove(container);
	}

}
