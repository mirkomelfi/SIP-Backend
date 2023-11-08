package sip.proyecto.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sip.proyecto.app.model.dao.IContainerDAO;
import sip.proyecto.app.model.dao.IItemDAO;
import sip.proyecto.app.model.dao.ILocationChangeDAO;
import sip.proyecto.app.model.entity.Container;
import sip.proyecto.app.model.entity.Image;
import sip.proyecto.app.model.entity.Item;
import sip.proyecto.app.model.entity.LocationChange;
import sip.proyecto.app.model.entity.User;

@Service
public class ItemService implements IItemService {

	@Autowired
	IItemDAO itemDAO;
	@Autowired
	IContainerDAO contDAO;
	@Autowired
	ILocationChangeDAO locationDAO;
	@Autowired
	IUserService usrService;

	@Override
	public List<Item> findAll() {
		try {
			List<Item> items = itemDAO.findAll();
			return items;

		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}

	}

	@Override
	public Item findById(long id) {
		// Devuelve el item si lo encuentra, sino tira un error
		Item item;
		try {
			item = this.itemDAO.findById(id);
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}
		if (item == null)
			throw new Error("No se encontro el item");
		return item;
	}

	@Override
	public void save(Item item) {
		try {
			this.itemDAO.save(item);
			return;
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}

	}

	@Override
	public void deleteById(long itemId) {
		try {
			this.itemDAO.deleteById(itemId);
			return;
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}

	}

	@Override
	public void updateById(long id, Item item) {
		try {
			Item newItem = itemDAO.findById(id);
			if (newItem != null) {
				if (item.getDescription() != null)
					newItem.setDescription(item.getDescription());
				if (item.getName() != null)
					newItem.setName(item.getName());

				itemDAO.save(newItem);
				return;
			}
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}

	}

	@Override
	public void addLocationToItem(long itemId, long contId) {
		Item item = itemDAO.findById(itemId);
		if (item == null)
			throw new Error("No se encontro el item");
		Container cont = contDAO.findById(contId);
		if (cont == null)
			throw new Error("No se encontro el contenedor");
		LocationChange lc = new LocationChange();
		User usr = this.usrService.findLogged();
		if (item.getCurrentLocation() != null) {
			lc.setDescription("Se cambio el item del contenedor ID: " + item.getCurrentLocation().getId()
					+ ", al contenedor ID:  " + contId);
		} else {
			lc.setDescription("Se agrego el item al contenedor ID: " + contId);
		}
		lc.setItem(item);
		lc.setUser(usr);

		item.getChanges().add(lc);
		item.setCurrentLocation(cont);
		try {
			itemDAO.save(item);
			return;
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}

	}

	@Override
	public List<Item> filterByName(String name) {
		List<Item> items;
		try {
			items = itemDAO.findAllByName(name);
		} catch (Throwable e) {
			throw new Error("Error al obtener los items (Error interno) "+e.getMessage());
		}
		if (items == null)
			throw new Error("Error al obtener los items");
		if (items.isEmpty())
			throw new Error("No se encontraron items con el nombre: " + name);
		return items;
	}

	@Override
	public void saveImage(Image image, int itemId) {
		Item item = this.findById(itemId);
		item.setItemImage(image);
		this.save(item);
		
	}

}