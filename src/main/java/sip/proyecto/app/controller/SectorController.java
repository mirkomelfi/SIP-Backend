package sip.proyecto.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sip.proyecto.app.model.entity.Sector;
import sip.proyecto.app.model.entity.SectorDTO;
import sip.proyecto.app.service.ISectorService;

@RestController
@RequestMapping("")
public class SectorController {
	@Autowired
	private ISectorService sectorService;	
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/sectors")
	public ResponseEntity<?> getSectors()
	{
		try {
			List<Sector> sectores= sectorService.findAll();
			List<SectorDTO> dtos = new ArrayList<SectorDTO>();
			for(Sector sec : sectores) 
			{
				dtos.add(new SectorDTO(sec));
			}
			return new ResponseEntity<>(dtos,HttpStatus.OK);
		}catch(Throwable e) {return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);}
						
	}
@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/sectors/{sectorId}")
	public ResponseEntity<?> getSectorById(@PathVariable long sectorId)
	{
		try {
			Sector sector= sectorService.findById(sectorId);
			SectorDTO dto= new SectorDTO(sector);
			
			return new ResponseEntity<>(dto,HttpStatus.OK);
		}catch(Throwable e) {return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);}
						
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@PostMapping("/admin/sectors")
	public ResponseEntity<?> addSector(@RequestBody Sector sector){
		try {
			sectorService.save(sector);
			return new ResponseEntity<>(new Message("Sector creado correctamente"),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@PutMapping("/admin/sectors/{sectorId}")
	public ResponseEntity<?> updateSector(@PathVariable int sectorId, @RequestBody SectorDTO dto){
		try {
			Sector sector = new Sector();
			sector.setDescription(dto.getDescription());
			sector.setName(dto.getName());
			sectorService.updateById(sectorId, sector);
			
			return new ResponseEntity<>(new Message("Sector actualizado correctamente"),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@DeleteMapping("/admin/sectors/{sectorId}")
	public ResponseEntity<?> deleteSector(@PathVariable long sectorId){
		try {
			sectorService.deleteById(sectorId);
			return new ResponseEntity<>(new Message("Sector eliminado correctamente"),HttpStatus.OK);
		}
		catch(Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()),HttpStatus.NOT_ACCEPTABLE);
		}
	}
	

	
}
