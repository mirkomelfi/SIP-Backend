package sip.proyecto.app.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sip.proyecto.app.model.entity.Item;

@Repository
public class ItemDAO implements IItemDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Item findById(long id) {
		Session currentSession = this.entityManager.unwrap(Session.class);
		Item item = currentSession.get(Item.class, id);
		if (item != null)
			return item;
		throw new Error("No se encontro el item");
	}

	@Override
	@Transactional
	public List<Item> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		String query = "FROM Item";
		List<Item> list = currentSession.createQuery(query, Item.class).getResultList();
		if (list.isEmpty())
			throw new Error("No hay items cargados");
		return list;
	}

	@Override
	@Transactional
	public void save(Item item) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.persist(item);

	}

	@Override
	@Transactional
	public void deleteById(long id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Item item = this.findById(id);
		currentSession.remove(item);
	}

	@Override
	public List<Item> findAllByName(String name) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Item> findQuery = currentSession.createQuery("FROM Item Where name LIKE :name OR description LIKE :name", Item.class);
		findQuery.setParameter("name", "%" + name + "%");
		List<Item> items = findQuery.getResultList();
		return items;
	}

}
