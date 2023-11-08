package sip.proyecto.app.model.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String name;
	String description;
	@ManyToOne
	Container currentLocation;
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
	List<LocationChange> changes;
	@OneToOne(cascade=CascadeType.ALL)
	Image itemImage;

	public Item() {
		super();
	}

	public Item(long id, String name, String description, List<LocationChange> changes) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.changes = changes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<LocationChange> getChanges() {
		return changes;
	}

	public void setChanges(List<LocationChange> changes) {
		this.changes = changes;
	}

	public Container getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Container currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Image getItemImage() {
		return itemImage;
	}

	public void setItemImage(Image itemImage) {
		this.itemImage = itemImage;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", changes=" + changes + "]";
	}

}
