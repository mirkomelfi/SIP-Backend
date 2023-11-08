package sip.proyecto.app.service;

import java.util.List;

import sip.proyecto.app.model.entity.Image;
import sip.proyecto.app.model.entity.Item;



public interface IItemService {

	public List<Item> findAll();
	public Item findById(long id);
	public void save(Item item);
	public void deleteById(long itemId);
	public void updateById(long itemId,Item item);
	public void addLocationToItem(long itemId,long contId);
	public List<Item> filterByName(String name);
	public void saveImage(Image iamge, int itemId);
	
}
