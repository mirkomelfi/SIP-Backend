package sip.proyecto.app.model.entity;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "containers")
public class Container {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String name;
	String description;
	@OneToMany(mappedBy = "currentLocation")
	List<Item> items;
	@ManyToOne
	Sector sector;

	public Container() {
		super();
	}

	public Container(long id, String name, String description, List<Item> items) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.items = items;
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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}
	
	@Override
	public String toString() {
		return "Container [id=" + id + ", name=" + name + ", description=" + description + ", items=" + items + "]";
	}

}
