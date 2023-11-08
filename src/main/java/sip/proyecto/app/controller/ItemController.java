package sip.proyecto.app.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sip.proyecto.app.model.entity.Image;
import sip.proyecto.app.model.entity.Item;
import sip.proyecto.app.model.entity.ItemDTO;
import sip.proyecto.app.service.IItemService;




@RestController
@RequestMapping("")
public class ItemController {
	@Autowired
	private IItemService itemService;	
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/items")
	public ResponseEntity<?> getItems()
	{
		try {
			List<Item> items= itemService.findAll();
			List<ItemDTO> dtos = new ArrayList<ItemDTO>();
			for(Item item : items) 
			{
				dtos.add(new ItemDTO(item));
			}
			return new ResponseEntity<>(dtos,HttpStatus.OK);
		}catch(Throwable e) {return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);}
						
	}
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/items/{itemId}")
	public ResponseEntity<?> getItemById(@PathVariable long itemId)
	{
		try {
			Item item= itemService.findById(itemId);
			ItemDTO dto= new ItemDTO(item);
			
			return new ResponseEntity<>(dto,HttpStatus.OK);
		}catch(Throwable e) {return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);}
						
	}
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@PostMapping("/items")
	public ResponseEntity<?> addItem(@RequestBody Item item){
		try {
			itemService.save(item);
			return new ResponseEntity<>(new ItemDTO(item),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@PutMapping("/items/{itemId}")
	public ResponseEntity<?> updateItem(@PathVariable int itemId, @RequestBody ItemDTO dto){
		try {
			Item item = new Item();
			item.setDescription(dto.getDescription());
			item.setName(dto.getName());
			itemService.updateById(itemId, item);
			
			return new ResponseEntity<>(new Message("Item actualizado correctamente"),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@DeleteMapping("/admin/items/{itemId}")
	public ResponseEntity<?> deleteItem(@PathVariable long itemId){
		try {
			itemService.deleteById(itemId);
			return new ResponseEntity<>(new Message("Item eliminado correctamente"),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@PutMapping("/items/{itemId}/newLocation/{idContainer}")
	public ResponseEntity<?> addLocationItem(@PathVariable long itemId,@PathVariable long idContainer){
		try {

			itemService.addLocationToItem(itemId,idContainer);
			return new ResponseEntity<>(new Message("Location creada y agregada al item correctamente"),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/items/filter")
	public ResponseEntity<?> getItemById(@RequestParam ("query")  String name)
	{
		try {
			List<Item> items = itemService.filterByName(name);
			List<ItemDTO> dtos = new ArrayList<ItemDTO>();
			for(Item i : items) 
			{
				dtos.add(new ItemDTO(i));
			}
			return new ResponseEntity<>(dtos,HttpStatus.NOT_ACCEPTABLE);
		}catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
						
	}
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@PutMapping("/items/{itemId}/updateImage")
	public ResponseEntity<?> addImagen(@RequestParam("archivo") MultipartFile archivo, @PathVariable int itemId) {
		String msj;
		try {
			Image image = new Image(archivo.getBytes());
			itemService.saveImage(image, itemId);
			msj = "Imagen subida exitosamente.";
			return ResponseEntity.ok(new Message(msj));
		} catch (Throwable e) {
			e.printStackTrace();
			msj = e.getMessage();
			return new ResponseEntity<>(new Message(msj), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
