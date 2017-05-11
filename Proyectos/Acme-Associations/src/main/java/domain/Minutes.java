
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Minutes extends Commentable {

	// Constructors -----------------------------------------------------------

	public Minutes() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	document;


	@NotNull
	@URL
	public String getDocument() {
		return this.document;
	}
	public void setDocument(final String document) {
		this.document = document;
	}


	// Relationships ----------------------------------------------------------

	private Meeting				about;
	private Collection<User>	isAttendedBy;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Meeting getAbout() {
		return this.about;
	}
	public void setAbout(final Meeting about) {
		this.about = about;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<User> getIsAttendedBy() {
		return this.isAttendedBy;
	}
	public void setIsAttendedBy(final Collection<User> isAttendedBy) {
		this.isAttendedBy = isAttendedBy;
	}

}
