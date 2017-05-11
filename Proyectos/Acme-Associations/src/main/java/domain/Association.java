
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Association extends Commentable {

	private String	name;
	private String	description;
	private String	address;
	private Date	creationDate;
	private String	statutes;
	private String	announcements;
	private String	picture;


	// Constructors -----------------------------------------------------------

	public Association() {
		super();
	}

	// Attributes -------------------------------------------------------------

	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	@URL
	@NotBlank
	public String getStatutes() {
		return this.statutes;
	}
	public void setStatutes(final String statutes) {
		this.statutes = statutes;
	}

	public String getAnnouncements() {
		return this.announcements;
	}
	public void setAnnouncements(final String announcements) {
		this.announcements = announcements;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	// Relationships ----------------------------------------------------------

}
