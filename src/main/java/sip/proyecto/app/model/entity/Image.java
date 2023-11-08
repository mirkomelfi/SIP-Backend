package sip.proyecto.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] imageData;


	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Image(byte[] data) {
		super();
		this.imageData = data;
	}

	public Long getId() {
		return id;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public void setId(Long id) {
		this.id = id;
	}



}
