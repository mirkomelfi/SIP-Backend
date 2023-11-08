package sip.proyecto.app.model.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sip.proyecto.app.model.entity.User;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
@Repository
public class UserDAO implements IUserDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public User findById(long id) {
		Session currentSession = this.entityManager.unwrap(Session.class);
		User usr = currentSession.get(User.class, id);
		if (usr!= null)
				return usr;
		throw new Error("No se encontro al usuario");
	}

	@Override
	@Transactional
	public List<User> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);
		String query = "FROM User";
		List<User> list = currentSession.createQuery(query, User.class).getResultList();
		if(list.isEmpty())
			throw new Error("No hay usuarios cargados");
		return list;
	}
	

	@Override
	@Transactional
	public void save(User user) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.persist(user);
	}

	@Override
	@Transactional
	public void deleteById(long id) {
		Session currentSession = entityManager.unwrap(Session.class);
		User usr = this.findById(id);
		currentSession.remove(usr);
	}

	@Override
	@Transactional
	public User findByUsername(String username) {
		Session currentSession = this.entityManager.unwrap(Session.class);
		Query<User> theQuery = currentSession.createQuery("FROM User WHERE username=:username", User.class);
		theQuery.setParameter("username", username);
		User usr = theQuery.uniqueResult();
		if (usr!= null)
				return usr;
		throw new Error("No se encontro al usuario");
	}

}
