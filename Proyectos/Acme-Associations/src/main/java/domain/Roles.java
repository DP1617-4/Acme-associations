
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
