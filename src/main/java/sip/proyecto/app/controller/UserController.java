package sip.proyecto.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sip.proyecto.app.Errors.LoggedAdminUsernameChangeError;
import sip.proyecto.app.config.JwtAuthFilter;
import sip.proyecto.app.model.entity.User;
import sip.proyecto.app.model.entity.UserDTO;
import sip.proyecto.app.service.IUserService;


@RestController
@RequestMapping("")
public class UserController {
	private final int EXPIRATION_TIME_IN_MIN = 6000; // En 10 mins expira el token
	
	@Autowired
	private IUserService userService;
	@Autowired
	private SecretKey secretKey; // Inyecta la clave secreta
	@Autowired
	private JwtAuthFilter jwt;

	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO credentials) {
		User user = null;
		boolean adminCreated = false;
		try {
			 user= userService.findByUsername(credentials.getUsername());CreateAdminIfEmptyDB(credentials);	
		}catch(Throwable e){
			//BORRAR ESTO EN LA ENTREGA
			if(user == null) 
			{
				user = CreateAdminIfEmptyDB(credentials);
				if(user != null)
					adminCreated = true;
			}
			// HASTA ACÁ
		}
		
		
		if (userService.checkLogin(credentials.getUsername(), credentials.getPassword()) != null || adminCreated) {
			// Crear el token JWT
			String token = Jwts.builder().setSubject(credentials.getUsername())

					.claim("rol", user.getRol())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MIN * 60 * 1000))
					.signWith(secretKey, SignatureAlgorithm.HS256).compact();

			return new ResponseEntity<>(new Token(token), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Message("Credenciales inválidas."), HttpStatus.UNAUTHORIZED);
		}
	}
	@CrossOrigin
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@PostMapping("/admin/register")
	public ResponseEntity<?> register (@RequestBody User usr)
	{
		try {		
			this.userService.create(usr);
			return new ResponseEntity<>(new Message("Usuario registrado"),HttpStatus.OK);
		}catch(Throwable e) {return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);}
						
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@GetMapping("/admin/users")
	public ResponseEntity<?> getUsers()
	{
		try {
			List<User> users= userService.findAll();
			List<UserDTO> dtos = new ArrayList<UserDTO>();
			for(User user : users) 
			{
				dtos.add(new UserDTO(user));

			}
			return new ResponseEntity<>(dtos,HttpStatus.OK);
		}catch(Throwable e) {return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);}
						
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@GetMapping("/admin/users/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable long userId)
	{
		try {
			User user= userService.findById(userId);
			UserDTO dto= new UserDTO(user);
			
			return new ResponseEntity<>(dto,HttpStatus.OK);
		}catch(Throwable e) {return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);}
						
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@PutMapping("/admin/users/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User data){
		try {

			User user = new User();
			user.setName(data.getName());
			user.setSurname(data.getSurname());
			user.setUsername(data.getUsername());
			user.setPassword(data.getPassword());
			userService.updateById(userId, user);
			
			return new ResponseEntity<>(new Message("Usuario actualizado correctamente"),HttpStatus.OK);
		}
		catch(LoggedAdminUsernameChangeError e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@DeleteMapping("/admin/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable long userId){
		try {
			userService.deleteById(userId);
			return new ResponseEntity<>(new Message("Usuario eliminado correctamente"),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/profile")
	public ResponseEntity<?> getLoggedUser() {
		try {
			User user = userService.findLogged();
			UserDTO dto = new UserDTO(user);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		}

	}
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@PutMapping("/updateProfile")//profile/update
	public ResponseEntity<?> updateLoggedUser(@RequestBody UserDTO data) {
		try {
			User user = new User();
			user.setName(data.getName());
			user.setSurname(data.getSurname());
			user.setUsername(data.getUsername());
			user.setPassword(data.getPassword());
			userService.updateLogged(user);		
			return new ResponseEntity<>(new Message("Perfil actualizado"), HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		}

	}

	
	// ELIMINAR ESTE METODO PARA LA ENTREGA FINAL
	private User CreateAdminIfEmptyDB(UserDTO credentials) 
	{
		String ADMIN_USERNAME = "admin";
		String ADMIN_PASSWORD = "admin";
		if(credentials.getUsername().contentEquals(ADMIN_USERNAME) && credentials.getPassword().contentEquals(ADMIN_PASSWORD)) 
		{
			try {
				this.userService.findAll().isEmpty();
				
			}catch(Throwable e) {
				User adminUsr = new User();
				adminUsr.setName("System Admin");
				adminUsr.setSurname("");
				adminUsr.setUsername(ADMIN_USERNAME);
				adminUsr.setPassword(ADMIN_PASSWORD);
				adminUsr.setRol("ROL_ADMIN");
				this.userService.create(adminUsr);
				return adminUsr;
			}
	
		}
		return null;
		
	}
}
