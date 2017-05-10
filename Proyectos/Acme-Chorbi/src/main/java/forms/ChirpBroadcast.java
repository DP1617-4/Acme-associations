
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Event;

public class ChirpBroadcast {

	public ChirpBroadcast() {
		super();
	}


	private String				attachment;
	private String				subject;
	private String				text;
	private Collection<String>	attachments;
	private Event				event;


	@NotBlank
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}
	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

	@ElementCollection
	public Collection<String> getAttachments() {
		return this.attachments;
	}
	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

	public String getAttachment() {

		return this.attachment;
	}
	public void setAttachment(final String attachment) {

		this.attachment = attachment;
	}

	@NotNull
	@Valid
	public Event getEvent() {
		return this.event;
	}
	public void setEvent(final Event event) {
		this.event = event;
	}
}
