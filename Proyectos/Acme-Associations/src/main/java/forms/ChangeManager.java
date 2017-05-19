
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Association;
import domain.User;

public class ChangeManager {

	public ChangeManager() {
		super();
	}


	private User		user;
	private Association	association;


	@NotBlank
	public User getUser() {
		return this.user;
	}
	public void setUser(final User user) {
		this.user = user;
	}

	@NotNull
	@Valid
	public Association getAssociation() {
		return this.association;
	}
	public void setAssociation(final Association association) {
		this.association = association;
	}
}
