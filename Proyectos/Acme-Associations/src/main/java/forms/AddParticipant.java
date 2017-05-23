
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Minutes;
import domain.User;

public class AddParticipant {

	public AddParticipant() {
		super();
	}


	private User	user;
	private Minutes	minute;


	@NotBlank
	public User getUser() {
		return this.user;
	}
	public void setUser(final User user) {
		this.user = user;
	}

	@NotNull
	@Valid
	public Minutes getMinute() {
		return this.minute;
	}
	public void setMinute(final Minutes minute) {
		this.minute = minute;
	}
}
