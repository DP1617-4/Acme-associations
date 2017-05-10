
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "relationshipType"), @Index(columnList = "birthDate"), @Index(columnList = "genre"), @Index(columnList = "banned"), @Index(columnList = "country"), @Index(columnList = "description"), @Index(columnList = "state"),
	@Index(columnList = "province"), @Index(columnList = "city")
})
public class Chorbi extends CreditHolder {

	// Constructors -----------------------------------------------------------

	public Chorbi() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	picture;
	private String	description;
	private String	relationshipType;
	private Date	birthDate;
	private String	genre;
	private Boolean	banned;
	private String	country;
	private String	state;
	private String	province;
	private String	city;


	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	@NotBlank
	@Pattern(regexp = "^ACTIVITIES|FRIENDSHIP|LOVE$")
	public String getRelationshipType() {
		return this.relationshipType;
	}
	public void setRelationshipType(final String relationshipType) {
		this.relationshipType = relationshipType;
	}
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}
	@NotBlank
	@Pattern(regexp = "^MAN|WOMAN$")
	public String getGenre() {
		return this.genre;
	}
	public void setGenre(final String genre) {
		this.genre = genre;
	}
	public Boolean getBanned() {
		return this.banned;
	}
	public void setBanned(final Boolean banned) {
		this.banned = banned;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountry(final String country) {
		this.country = country;
	}
	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}
	public String getProvince() {
		return this.province;
	}
	public void setProvince(final String province) {
		this.province = province;
	}
	@NotBlank
	public String getCity() {
		return this.city;
	}
	public void setCity(final String city) {
		this.city = city;
	}

	// Relationships ----------------------------------------------------------

}
