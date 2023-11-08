package sip.proyecto.app.model.dao;

import java.util.List;

import sip.proyecto.app.model.entity.Item;


public interface IItemDAO {
	public Item findById(long id);

	public List<Item> findAll();

	public void save(Item user);

	public void deleteById(long Id);

	public List<Item> findAllByName(String name);

}
