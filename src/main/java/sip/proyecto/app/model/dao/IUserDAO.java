package sip.proyecto.app.model.dao;

import java.util.List;

import sip.proyecto.app.model.entity.User;

public interface IUserDAO {
	public User findById(long id);

	public List<User> findAll();

	public void save(User user);

	public void deleteById(long Id);

	public User findByUsername(String username);
	


}
