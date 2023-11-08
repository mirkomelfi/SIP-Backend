package sip.proyecto.app.model.entity;

import java.util.ArrayList;
import java.util.List;

public class ItemDTO {
	long id;
	long containerID;
	String name;
	String description;
	List<LocationChangeDTO> changes;
	Image image;
	public ItemDTO() {
		super();
	}

	public ItemDTO(Item item) {
		super();
		this.id = item.getId();
		if (item.getCurrentLocation() != null)
			this.containerID = item.getCurrentLocation().getId();
		this.name = item.getName();
		this.description = item.getDescription();
		this.changes = new ArrayList<LocationChangeDTO>();
		if (item.getChanges() != null) {
			for (LocationChange change : item.getChanges()) {
				this.changes.add(new LocationChangeDTO(change));
			}
		}
		this.image = item.getItemImage();
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

	public List<LocationChangeDTO> getChanges() {
		return changes;
	}

	public void setChanges(List<LocationChangeDTO> changes) {
		this.changes = changes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getContainerID() {
		return containerID;
	}

	public void setContainerID(long containerID) {
		this.containerID = containerID;
	}
	

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ItemDTO [name=" + name + ", description=" + description + ", changes=" + changes + "]";
	}

}
