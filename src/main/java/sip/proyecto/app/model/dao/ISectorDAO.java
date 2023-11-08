package sip.proyecto.app.model.dao;

import java.util.List;

import sip.proyecto.app.model.entity.Sector;



public interface ISectorDAO {
	
	public Sector findById(long id);

	public List<Sector> findAll();

	public void save(Sector sector);

	public void deleteById(long id);

}
