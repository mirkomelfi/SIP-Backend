package sip.proyecto.app.model.entity;

import java.util.ArrayList;
import java.util.List;

public class ContainerDTO {
	long id;
	long sectorID;
	String name;
	String description;
	List<ItemDTO> items;

	public ContainerDTO() {
		super();
	}

	public ContainerDTO(Container container) {
		super();
		this.id = container.getId();
		this.sectorID = container.getSector().getId();
		this.name = container.getName();
		this.description = container.getDescription();
		this.items = new ArrayList<ItemDTO>();
		if (container.getItems() != null) {
			for (Item item : container.getItems()) {
				this.items.add(new ItemDTO(item));
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

	public List<ItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSectorID() {
		return sectorID;
	}

	public void setSectorID(long sectorID) {
		this.sectorID = sectorID;
	}

	@Override
	public String toString() {
		return "ContainerDTO [name=" + name + ", description=" + description + ", items=" + items + "]";
	}

}
