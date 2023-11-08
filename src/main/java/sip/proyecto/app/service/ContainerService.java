package sip.proyecto.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sip.proyecto.app.model.dao.IContainerDAO;
import sip.proyecto.app.model.dao.IItemDAO;
import sip.proyecto.app.model.dao.ISectorDAO;
import sip.proyecto.app.model.entity.Container;
import sip.proyecto.app.model.entity.Item;
import sip.proyecto.app.model.entity.Sector;

@Service
public class ContainerService implements IContainerService {

	@Autowired
	IContainerDAO containerDAO;
	@Autowired
	ISectorDAO sectorDAO;
	@Autowired
	IItemDAO itemDAO;

	@Override
	public List<Container> findAll() {
		try {
			List<Container> containers = containerDAO.findAll();
			return containers;

		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}

	}

	@Override
	public Container findById(long id) {

		Container container = this.containerDAO.findById(id);
		if (container == null)
			throw new Error("No se encontro el container");
		return container;

	}

	@Override
	public void save(Container container, long sectorId) {
		try {
			Sector sector = sectorDAO.findById(sectorId);
			if (sector == null) {
				throw new Error("El sector ingresado no existe");
			}
			container.setSector(sector);
			containerDAO.save(container);
			return;
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}

	}

	@Override
	public void deleteById(long containerId) {
		try {
			this.containerDAO.deleteById(containerId);
			return;
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}

	}

	@Override
	public void updateById(long id, Container container) {
		Container newContainer = containerDAO.findById(id);
		if (newContainer == null)
			throw new Error("No se encontró el container");
		if (container.getDescription() != null)
			newContainer.setDescription(container.getDescription());
		if (container.getName() != null)
			newContainer.setName(container.getName());

		try {
			containerDAO.save(newContainer);
			return;
		} catch (Throwable e) {
			throw new Error("Error al guardar contenedor: " + e.getMessage());
		}

	}

	public void addItemToContainer(int containerId, int itemId) {
		Container container = this.findById(containerId);
		Item item = itemDAO.findById(itemId);
		if(item == null)
			throw new Error("No se encontro el item");
		item.setCurrentLocation(container);
		try {
			itemDAO.save(item);
			return;
		} catch (Throwable e) {
			throw new Error("Error al cambiar la ubicacion del item");
		}
	}

	@Override
	public void addItemToContainer(int containerId, Item item) {
		Container cont = this.findById(containerId);
		item.setCurrentLocation(cont);
		try {
			itemDAO.save(item);
		} catch (Throwable e) {
			throw new Error("Error al guardar el item. Chequee los datos ingresados");
		}

	}

	@Override
	public List<Container> filterById(long idCont) {
		List<Container> result = new ArrayList<Container>();
		Container cont = this.findById(idCont);
		result.add(cont);
		return result;
	}

}