
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SearchTemplate extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public SearchTemplate() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	relationshipType;
	private Integer	age;
	private String	genre;
	private String	keyword;
	private Date	moment;
	private String	country;
	private String	state;
	private String	province;
	private String	city;


	@Pattern(regexp = "^ACTIVITIES|FRIENDSHIP|LOVE|\\s*$")
	public String getRelationshipType() {
		return this.relationshipType;
	}
	public void setRelationshipType(final String relationshipType) {
		this.relationshipType = relationshipType;
	}
	@Min(18)
	public Integer getAge() {
		return this.age;
	}
	public void setAge(final Integer age) {
		this.age = age;
	}

	@Pattern(regexp = "^MAN|WOMAN|\\s*$")
	public String getGenre() {
		return this.genre;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}
	public String getKeyword() {
		return this.keyword;
	}
	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
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

	public String getCity() {
		return this.city;
	}
	public void setCity(final String city) {
		this.city = city;
	}


	// Relationships ----------------------------------------------------------
	private Collection<Chorbi>	chorbies;
	private Chorbi				chorbi;


	@Valid
	@OneToOne(optional = false)
	@NotNull
	public Chorbi getChorbi() {
		return this.chorbi;
	}
	public void setChorbi(final Chorbi chorbi) {
		this.chorbi = chorbi;
	}

	@Valid
	@ManyToMany(cascade = CascadeType.ALL)
	@NotNull
	public Collection<Chorbi> getChorbies() {
		return this.chorbies;
	}
	public void setChorbies(final Collection<Chorbi> cache) {
		this.chorbies = cache;
	}

}
