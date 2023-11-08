package sip.proyecto.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sip.proyecto.app.model.dao.IContainerDAO;
import sip.proyecto.app.model.dao.ISectorDAO;
import sip.proyecto.app.model.entity.Sector;

@Service
public class SectorService implements ISectorService {

	@Autowired
	ISectorDAO sectorDAO;
	@Autowired
	IContainerDAO containerDAO;

	@Override
	public List<Sector> findAll() {

		List<Sector> sectors = sectorDAO.findAll();
		if (sectors == null)
			throw new Error("Error al obtener sectores");
		if (sectors.isEmpty())
			throw new Error("No se encontraron sectores. Cree un sector");
		return sectors;

	}

	@Override
	public Sector findById(long id) {

		Sector sector = this.sectorDAO.findById(id);
		if(sector == null)
			throw new Error("No se encontro el sector");
		return sector;

	}

	@Override
	public void save(Sector sector) {
		try {
			this.sectorDAO.save(sector);
			return;
		} catch (Throwable e) {
			throw new Error("Error al guardar sector");
		}

	}

	@Override
	public void deleteById(long sectorId) {
		try {
			this.sectorDAO.deleteById(sectorId);
			return;
		} catch (Throwable e) {
			throw new Error("Error al eliminar el sector: "+e.getMessage());
		}

	}

	@Override
	public void updateById(long id, Sector sector) {
		Sector newSector = sectorDAO.findById(id);
		if (newSector == null)
			throw new Error("No se encontro el sector");
		if (sector.getDescription() != null)
			newSector.setDescription(sector.getDescription());
		if (sector.getName() != null)
			newSector.setName(sector.getName());

		try {
			sectorDAO.save(newSector);
		} catch (Throwable e) {
			throw new Error("Error al actualizar datos del sector: " + e.getMessage());
		}

	}

//	public void addContainerToSector(int sectorId, int containerId) {
//		try {
//			Sector sector= sectorDAO.findById(sectorId);
//			Container container= containerDAO.findById(containerId);
//			//if (sector!=null&&container!=null&&!sector.PerteneceAlSector(container))  VALIDAR SI YA PERTENECE??
//			container.setSector(sector);
//			sectorDAO.save(sector);
//		}
//		catch(Throwable e) {
//			throw new Error("Error al actualizar datos del sector");
//		}
//	}

}