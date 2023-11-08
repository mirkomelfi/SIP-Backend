package sip.proyecto.app.service;

import java.util.List;

import sip.proyecto.app.model.entity.LocationChange;


public interface ILocationChangeService {

	public List<LocationChange> findAll();
	public LocationChange findById(long id);
	public void save(LocationChange location);
	public void deleteById(long locationId);
	public void updateById(long locationId,LocationChange location);
	
}
