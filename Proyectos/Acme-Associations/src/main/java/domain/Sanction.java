
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Sanction extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Sanction() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	motiff;
	private Date	endDate;


	@NotBlank
	public String getMotiff() {
		return this.motiff;
	}
	public void setMotiff(final String motiff) {
		this.motiff = motiff;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getMoment() {
		return this.endDate;
	}
	public void setMoment(final Date endDate) {
		this.endDate = endDate;
	}


	// Relationships ----------------------------------------------------------

	private Association	relatedTo;
	private User		assignedTo;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Association getRelatedTo() {
		return this.relatedTo;
	}
	public void setRelatedTo(final Association relatedTo) {
		this.relatedTo = relatedTo;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getAssignedTo() {
		return this.assignedTo;
	}
	public void setAssignedToBy(final User assignedTo) {
		this.assignedTo = assignedTo;
	}

}
