
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Event() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	title;
	private String	description;
	private Date	moment;
	private Integer	numberSeat;
	private String	picture;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Min(0)
	@NotNull
	public Integer getNumberSeat() {
		return this.numberSeat;
	}
	public void setNumberSeat(final Integer numberSeat) {
		this.numberSeat = numberSeat;
	}


	// Relationships ----------------------------------------------------------

	private Manager				organiser;
	private Collection<Chorbi>	registered;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Manager getOrganiser() {
		return this.organiser;
	}
	public void setOrganiser(final Manager organiser) {
		this.organiser = organiser;
	}

	@Valid
	@ManyToMany(cascade = CascadeType.ALL)
	@NotNull
	public Collection<Chorbi> getRegistered() {
		return this.registered;
	}
	public void setRegistered(final Collection<Chorbi> registered) {
		this.registered = registered;
	}

}
