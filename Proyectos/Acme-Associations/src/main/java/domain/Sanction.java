
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "endDate"), @Index(columnList = "association_id"), @Index(columnList = "user_id")
})
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
	public Date getEndDate() {
		return this.endDate;
	}
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}


	// Relationships ----------------------------------------------------------

	private Association	association;
	private User		user;


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
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}
	public void setUser(final User user) {
		this.user = user;
	}

}
