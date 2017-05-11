
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Activity extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Activity() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;
	private String	description;
	private Integer	maximumAttendants;
	private Date	startMoment;
	private Date	endMoment;
	private Boolean	publicActivity;


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

	@Min(0)
	@NotNull
	public Integer getMaximumAttendants() {
		return this.maximumAttendants;
	}
	public void setMaximumAttendants(final Integer maximumAttendants) {
		this.maximumAttendants = maximumAttendants;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}
	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return this.endMoment;
	}
	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

	public Boolean getPublicActivity() {
		return this.publicActivity;
	}
	public void setPublicActivity(final Boolean publicActivity) {
		this.publicActivity = publicActivity;
	}


	// Relationships ----------------------------------------------------------

	private Association			association;
	private Collection<User>	users;
	private Place				place;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Association getAssociation() {
		return this.association;
	}
	public void setAssociation(final Association association) {
		this.association = association;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<User> getUsers() {
		return this.users;
	}
	public void setUsers(final Collection<User> users) {
		this.users = users;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Place getPlace() {
		return this.place;
	}
	public void setPlace(final Place place) {
		this.place = place;
	}

}
