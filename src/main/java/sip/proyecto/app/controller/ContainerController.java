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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sip.proyecto.app.model.entity.Container;
import sip.proyecto.app.model.entity.ContainerDTO;
import sip.proyecto.app.model.entity.Item;
import sip.proyecto.app.model.entity.ItemDTO;
import sip.proyecto.app.service.IContainerService;

@RestController
@RequestMapping("")
public class ContainerController {
	@Autowired
	private IContainerService containerService;
	@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/containers")
	public ResponseEntity<?> getContainers() {
		try {
			List<Container> containers = containerService.findAll();
			List<ContainerDTO> dtos = new ArrayList<ContainerDTO>();
			for (Container container : containers) {
				dtos.add(new ContainerDTO(container));
			}
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}

	}
	@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@RequestMapping("/**")
	public ResponseEntity<?> allRoutes() {
		return new ResponseEntity<>("No podes", HttpStatus.FORBIDDEN);

	}
	@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/containers/filter")
	public ResponseEntity<?> filterById(@RequestParam("idCont") String idCont) {

		try {
			long id;
			if (idCont.contentEquals("")) {
				id = 0;
			} else {
				id = Long.parseLong(idCont);
			}
			List<Container> containers;
			if (id == 0) {
				containers = containerService.findAll();
			} else {
				containers = containerService.filterById(id);
			}
			List<ContainerDTO> dtos = new ArrayList<ContainerDTO>();
			for (Container container : containers) {
				dtos.add(new ContainerDTO(container));
			}
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(new Message("La busqueda no corresponde a un numero de ID"),
					HttpStatus.NOT_ACCEPTABLE);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message("Error: " + e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@GetMapping("/containers/{containerId}")
	public ResponseEntity<?> getContainerById(@PathVariable long containerId) {
		try {
			Container container = containerService.findById(containerId);
			ContainerDTO dto = new ContainerDTO(container);

			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}

	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@PostMapping("/admin/sector/{sectorId}/containers")
	public ResponseEntity<?> addContainer(@PathVariable int sectorId, @RequestBody Container container) {
		try {
			containerService.save(container, sectorId);
			return new ResponseEntity<>(new Message("Container agregado correctamente al sector"), HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}

	}
	@PreAuthorize("hasAuthority('ROL_ADMIN')")
	@PutMapping("/admin/containers/{containerId}")
	public ResponseEntity<?> updateContainer(@PathVariable int containerId, @RequestBody ContainerDTO dto) {
		try {
			Container container = new Container();
			container.setDescription(dto.getDescription());
			container.setName(dto.getName());
			containerService.updateById(containerId, container);

			return new ResponseEntity<>(new Message("Container actualizado correctamente"), HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@DeleteMapping("/admin/containers/{containerId}")
	public ResponseEntity<?> deleteContainer(@PathVariable long containerId) {
		try {
			containerService.deleteById(containerId);
			return new ResponseEntity<>(new Message("Container eliminado correctamente"), HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@PutMapping("/containers/{containerId}/addItem/{itemId}")
	public ResponseEntity<?> addItemContainer(@PathVariable int containerId, @PathVariable int itemId) {
		try {
			containerService.addItemToContainer(containerId, itemId);
			return new ResponseEntity<>(new Message("Item agregado al container correctamente"), HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}
	}
	@PreAuthorize("hasAuthority('ROL_ADMIN') or hasAuthority('ROL_USER')")
	@PostMapping("/containers/{containerId}/addItem")
	public ResponseEntity<?> addItem(@PathVariable int containerId, @RequestBody ItemDTO dto) {
		Item item = new Item();
		item.setDescription(dto.getDescription());
		item.setName(dto.getName());
		try {
			containerService.addItemToContainer(containerId, item);
			return new ResponseEntity<>(new ItemDTO(item),HttpStatus.OK);
		} catch (Throwable e) {
			return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
