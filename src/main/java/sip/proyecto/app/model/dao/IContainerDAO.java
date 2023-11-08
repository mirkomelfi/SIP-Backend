package sip.proyecto.app.model.dao;

import java.util.List;

import sip.proyecto.app.model.entity.Container;


public interface IContainerDAO {
	public Container findById(long id);

	public List<Container> findAll();

	public void save(Container container);

	public void deleteById(long id);

}
