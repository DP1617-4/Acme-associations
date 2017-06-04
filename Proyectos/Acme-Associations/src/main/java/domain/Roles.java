
package domain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

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
	@SafeHtml
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

	public static List<String> values() {
		final Field[] declaredFields = String.class.getDeclaredFields();
		final List<String> staticFields = new ArrayList<String>();
		for (final Field field : declaredFields)
			if (java.lang.reflect.Modifier.isStatic(field.getModifiers()))
				staticFields.add(field.toString());
		return staticFields;
	}

}
