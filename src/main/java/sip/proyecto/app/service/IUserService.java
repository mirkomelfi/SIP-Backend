package sip.proyecto.app.service;

import java.util.List;

import sip.proyecto.app.model.entity.User;

public interface IUserService {
	public List<User> findAll();
	public User findById(long id);
	public User findByUsername(String username);
	public User checkLogin(String username, String password);
	public void save(User usr);
	public void deleteById(long id);
	public void updateById(long id,User usr);
	public User findLogged();
	public void updateLogged(User user);
	void create(User user);
}
