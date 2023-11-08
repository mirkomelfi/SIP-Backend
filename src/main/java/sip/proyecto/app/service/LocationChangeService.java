package sip.proyecto.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sip.proyecto.app.model.dao.ILocationChangeDAO;
import sip.proyecto.app.model.entity.LocationChange;


@Service
public class LocationChangeService implements ILocationChangeService {

	@Autowired
	ILocationChangeDAO locationDAO;

	@Override
	public List<LocationChange> findAll() {
		try {
			List<LocationChange> locations= locationDAO.findAll();
			return locations;

		}catch (Throwable e) {
			throw new Error("Error al buscar los locations");
		}

	}
	
	@Override
	public LocationChange findById(long id) {
		// Devuelve el location si lo encuentra, sino tira un error
		try {
			LocationChange location = this.locationDAO.findById(id);
			return location;
		} catch (Throwable e) {
			throw new Error("Error al buscar el location");
		}

	}

	@Override
	public void save(LocationChange location) {
		try {
			this.locationDAO.save(location);
			return;
		} catch (Throwable e) {
			throw new Error("Error al guardar la location");
		}

	}

	@Override
	public void deleteById(long locationId) {
		try {
			this.locationDAO.deleteById(locationId);
			return;
		} catch (Throwable e) {
			throw new Error("Error al eliminar la location");
		}

	}

	@Override
	public void updateById(long id, LocationChange location) {
		try {
			LocationChange newLoc= locationDAO.findById(id);
			if (newLoc!=null) {
				if(location.getDescription() != null) newLoc.setDescription(location.getDescription());
				locationDAO.save(newLoc);			
			}
		}
		catch(Throwable e) {
			throw new Error("Error al actualizar datos de la location");
		}

		
	}

}