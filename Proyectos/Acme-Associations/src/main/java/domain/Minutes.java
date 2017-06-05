
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "meeting_id")
})
public class Minutes extends Commentable {

	// Constructors -----------------------------------------------------------

	public Minutes() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	document;


	@NotBlank
	@URL
	public String getDocument() {
		return this.document;
	}
	public void setDocument(final String document) {
		this.document = document;
	}


	// Relationships ----------------------------------------------------------

	private Meeting				meeting;
	private Collection<User>	users;


	@Valid
	@NotNull
	@OneToOne
	public Meeting getMeeting() {
		return this.meeting;
	}
	public void setMeeting(final Meeting meeting) {
		this.meeting = meeting;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<User> getUsers() {
		return this.users;
	}
	public void setUsers(final Collection<User> users) {
		this.users = users;
	}

}
