package sip.proyecto.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sip.proyecto.app.Errors.LoggedAdminUsernameChangeError;
import sip.proyecto.app.config.JwtAuthFilter;
import sip.proyecto.app.model.dao.IUserDAO;
import sip.proyecto.app.model.entity.User;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserDAO userDAO;
	@Autowired
	JwtAuthFilter jwt;

	@Override
	public void create(User user) {
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encoded = passwordEncoder.encode(user.getPassword());
			user.setPassword(encoded);
			this.userDAO.save(user);
			return;
		} catch (Throwable e) {
			throw new Error("Error al guardar usuario: " + e.getMessage());
		}
	}

	@Override
	public List<User> findAll() {
		try {
			List<User> usuarios = userDAO.findAll();
			if (usuarios.size() != 0) {
				return usuarios;
			}
		} catch (Throwable e) {
			throw new Error("Error al buscar los usuarios");
		}

		return null;
	}

	@Override
	public User findById(long id) {
		// Devuelve el usuario si lo encuentra, sino tira un error
		try {
			User usr = this.userDAO.findById(id);
			return usr;
		} catch (Throwable e) {
			throw new Error("Error al buscar el usuario");
		}

	}

	@Override
	public User checkLogin(String username, String password) {
		try {
			User user = userDAO.findByUsername(username);
			if (passwordMatches(password, user.getPassword()))
				return user;
		} catch (Throwable e) {
			return null;
		}
		return null;
	}

	@Override
	public User findByUsername(String username) {
		// Devuelve el usuario si lo encuentra, sino tira un error
		try {
			User usr = this.userDAO.findByUsername(username);
			return usr;
		} catch (Throwable e) {
			throw new Error("Error al buscar el usuario");
		}
	}

	@Override
	public void save(User usr) {
		try {
			this.userDAO.save(usr);
			return;
		} catch (Throwable e) {
			throw new Error("Error al guardar usuario: " + e.getMessage());
		}

	}

	@Override
	public void deleteById(long usrId) {
		if(this.findById(usrId).getUsername().contentEquals(jwt.getUsername()))
			throw new Error("El usuario seleccionado no puede eliminarse");
		try {
			this.userDAO.deleteById(usrId);
			return;
		} catch (Throwable e) {
			throw new Error("Error al eliminar el usuario");
		}

	}

	@Override
	public void updateById(long id, User usr) {
		User newUser = userDAO.findById(id);
		String msj = "";
		boolean usernameChangeFailed = false;
		if (newUser == null)
			throw new Error("No se encontro el usuario");
		if (usr.getSurname() != null)
			newUser.setSurname(usr.getSurname());
		if (usr.getName() != null)
			newUser.setName(usr.getName());
		if (usr.getUsername() != null) {
			if (newUser.getUsername().contentEquals(jwt.getUsername())) {
				usernameChangeFailed = true;
			} else {
				newUser.setUsername(usr.getUsername());
			}
		}
		if (usr.getPassword() != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encondedPass = passwordEncoder.encode(usr.getPassword());
			newUser.setPassword(encondedPass);
		}
		try {
			userDAO.save(newUser);
		} catch (Throwable e) {
			throw new Error("Error al actualizar datos del usuario:" + e.getMessage());
		}
		if(usernameChangeFailed) {
			if(usr.getName() == null && usr.getPassword() == null && usr.getSurname() == null) {
				msj = "El username NO fue modificado (No permitido)";					
			}else {
				msj = "Usuario actualizado. El username NO fue modificado (No permitido)";
			}
			throw new LoggedAdminUsernameChangeError(msj);
		}

	}

	@Override
	public User findLogged() {
		String username;
		try {
			username = jwt.getUsername();
		} catch (Throwable e) {
			throw new Error("Error al obtener usuario loggeado: " + e.getMessage());
		}
		User usr = this.findByUsername(username);
		if (usr == null)
			throw new Error("No se encontro al usuario loggeado");
		return usr;

	}

	@Override
	public void updateLogged(User user) {

		User current = this.findLogged();
		if (user.getName() != null)
			current.setName(user.getName());
		if (user.getSurname() != null)
			current.setSurname(user.getSurname());
		if (user.getUsername() != null)
			current.setUsername(user.getUsername());
		if (user.getPassword() != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encondedPass = passwordEncoder.encode(user.getPassword());
			current.setPassword(encondedPass);
		}
		try {
			userDAO.save(current);
		} catch (Throwable e) {
			throw new Error("Error al modificar el usuario: " + e.getMessage());
		}
	}

	private boolean passwordMatches(String password, String passwordDB) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println("Password: " + password);
		System.out.println("passwordDB: " + passwordDB);
		boolean isPasswordMatch = passwordEncoder.matches(password, passwordDB);
		return isPasswordMatch;
	}

}
