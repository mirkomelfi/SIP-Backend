package sip.proyecto.app.service;

import java.util.List;

import sip.proyecto.app.model.entity.Container;
import sip.proyecto.app.model.entity.Item;


public interface IContainerService {

	public List<Container> findAll();
	public Container findById(long id);
	public void save(Container container,long sectorId);
	public void deleteById(long containerId);
	public void updateById(long containerId,Container container);
	public void addItemToContainer(int containerId, int itemId);
	public void addItemToContainer(int containerId, Item item);
	public List<Container> filterById(long idCont);
	
}
