package sip.proyecto.app.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "sectors")
public class Sector {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String name;
	String description;
	@OneToMany(mappedBy = "sector",cascade = CascadeType.ALL, orphanRemoval = true)
	List<Container> containers;

	public Sector() {
		super();
	}

	public Sector(long id, String name, String description, List<Container> containers) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.containers = containers;
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

	public List<Container> getContainers() {
		return containers;
	}

	public void setContainers(List<Container> containers) {
		this.containers = containers;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", name=" + name + ", description=" + description + ", containers=" + containers
				+ "]";
	}

}
