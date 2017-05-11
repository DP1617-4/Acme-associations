
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Roles extends DomainEntity {

	private String	type;


	// Constructors -----------------------------------------------------------

	public Roles() {
		super();
	}


	// Values -----------------------------------------------------------------

	public static final String	MANAGER			= "MANAGER";
	public static final String	COLLABORATOR	= "COLLABORATOR";
	public static final String	ASSOCIATE		= "ASSOCIATE";


	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^" + Roles.MANAGER + "|" + Roles.COLLABORATOR + "|" + Roles.ASSOCIATE + "$")
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}


	// Relationships ----------------------------------------------------------

	private Association	belongsTo;
	private User		assignedTo;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Association getBelongsTo() {
		return this.belongsTo;
	}
	public void setBelongsTo(final Association belongsTo) {
		this.belongsTo = belongsTo;
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
