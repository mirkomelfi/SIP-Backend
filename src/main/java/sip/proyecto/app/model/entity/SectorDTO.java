package sip.proyecto.app.model.entity;

import java.util.ArrayList;
import java.util.List;

public class SectorDTO {
	long id;
	String name;
	String description;
	List<ContainerDTO> containers;

	public SectorDTO() {
		super();
	}

	public SectorDTO(Sector sector) {
		super();
		this.id=sector.getId();
		this.name = sector.getName();
		this.description = sector.getDescription();
		this.containers = new ArrayList<ContainerDTO>();
		if(sector.getContainers()!= null) {
			for(Container c: sector.getContainers()) 
			{
				this.containers.add(new ContainerDTO(c));
			}
		}
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ContainerDTO> getContainers() {
		return containers;
	}

	public void setContainers(List<ContainerDTO> containers) {
		this.containers = containers;
	}
	
	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "SectorDTO [name=" + name + ", description=" + description + ", containers=" + containers + "]";
	}

}
