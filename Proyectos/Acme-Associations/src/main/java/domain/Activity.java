
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import validators.Moments;

@Moments
@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "association_id"), @Index(columnList = "startMoment"), @Index(columnList = "endMoment")
})
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

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}
	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@NotNull
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
	private Collection<User>	attendants;
	private Place				place;
	private User				winner;
	private Item				item;


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
	public Collection<User> getAttendants() {
		return this.attendants;
	}
	public void setAttendants(final Collection<User> attendants) {
		this.attendants = attendants;
	}

	@Valid
	@OneToOne(optional = true)
	public User getWinner() {
		return this.winner;
	}
	public void setWinner(final User winner) {
		this.winner = winner;
	}

	@Valid
	@OneToOne(optional = true)
	public Place getPlace() {
		return this.place;
	}
	public void setPlace(final Place place) {
		this.place = place;
	}

	@Valid
	@OneToOne(optional = true)
	public Item getItem() {
		return this.item;
	}
	public void setItem(final Item item) {
		this.item = item;
	}

}
