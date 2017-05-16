
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Association;

public class MessageBroadcast {

	public MessageBroadcast() {
		super();
	}


	private String		text;
	private Association	association;


	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
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
