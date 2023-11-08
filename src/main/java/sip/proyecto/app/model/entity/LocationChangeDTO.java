package sip.proyecto.app.model.entity;

public class LocationChangeDTO {
	long id;
	String description;
	String username;
	String completeName;

	public LocationChangeDTO() {
		super();
	}

	public LocationChangeDTO(LocationChange location) {
		super();
		this.id = location.getId();
		this.description = location.getDescription();
		if (location.getUser() != null) {
			this.username = location.getUser().getUsername();
			this.completeName = location.getUser().getName() +" "+ location.getUser().getSurname();
		}

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LocationChangeDTO [description=" + description + "]";
	}

}
