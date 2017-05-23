
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Activity;
import domain.User;

public class AddWinner {

	public AddWinner() {
		super();
	}


	private User		user;
	private Activity	activity;


	@NotBlank
	public User getUser() {
		return this.user;
	}
	public void setUser(final User user) {
		this.user = user;
	}

	@NotNull
	@Valid
	public Activity getActivity() {
		return this.activity;
	}
	public void setActivity(final Activity activity) {
		this.activity = activity;
	}
}
