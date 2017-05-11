
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Section() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}


	// Relationships ----------------------------------------------------------

	private Association	belongsTo;
	private User		managedBy;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Association getRelatedTo() {
		return this.belongsTo;
	}
	public void setBelongsTo(final Association belongsTo) {
		this.belongsTo = belongsTo;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getManagedBy() {
		return this.managedBy;
	}
	public void setManagedBy(final User managedBy) {
		this.managedBy = managedBy;
	}

}
