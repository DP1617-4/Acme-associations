
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "association_id")
})
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
