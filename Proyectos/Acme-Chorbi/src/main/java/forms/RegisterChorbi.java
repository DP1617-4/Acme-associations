package forms;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class RegisterChorbi {
	
	public RegisterChorbi(){
		super();
	}
	
	private String	name;
	private String	surname;
	private String	email;
	private String	phoneNumber;
	
	private String	picture;
	private String	description;
	private String	relationshipType;
	private Date	birthDate;
	private String	genre;
	private String	country;
	private String	state;
	private String	province;
	private String	city;
	
	private String username;
	private String password;
	
	private boolean accept;

	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "^([+](9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1))?\\d{1,14}$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
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
	
	@Size(min = 5, max = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
	
	public boolean isAccept() {
		return accept;
	}
	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	
	
	
}
