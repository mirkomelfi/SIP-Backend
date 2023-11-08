package sip.proyecto.app.model.dao;

import java.util.List;

import sip.proyecto.app.model.entity.LocationChange;



public interface ILocationChangeDAO {
	
	public LocationChange findById(long id);

	public List<LocationChange> findAll();

	public void save(LocationChange location);

	public void deleteById(long id);

}
