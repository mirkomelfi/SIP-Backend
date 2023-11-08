package sip.proyecto.app.service;

import java.util.List;

import sip.proyecto.app.model.entity.Sector;



public interface ISectorService {

	public List<Sector> findAll();
	public Sector findById(long id);
	public void save(Sector sector);
	public void deleteById(long sectorId);
	public void updateById(long sectorId,Sector sector);
	//public void addContainerToSector(int sectorId, int containerId);
	
}
