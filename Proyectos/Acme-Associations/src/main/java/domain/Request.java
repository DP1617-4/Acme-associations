
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Request() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Association	issuedTo;
	private User		issuedBy;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Association getIssuedTo() {
		return this.issuedTo;
	}
	public void setIssuedTo(final Association issuedTo) {
		this.issuedTo = issuedTo;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getIssuedBy() {
		return this.issuedBy;
	}
	public void setIssuedBy(final User issuedBy) {
		this.issuedBy = issuedBy;
	}

}
