
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "folder_id"), @Index(columnList = "sender_id"), @Index(columnList = "recipient_id")
})
public class Chirp extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Chirp() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String				subject;
	private String				text;
	private Collection<String>	attachments;
	private Date				moment;


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

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships ----------------------------------------------------------

	private Folder	folder;
	private Actor	sender;
	private Actor	recipient;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Folder getFolder() {
		return this.folder;
	}
	public void setFolder(final Folder folder) {
		this.folder = folder;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	//	@NotFound(action = NotFoundAction.IGNORE)
	public Actor getSender() {
		return this.sender;
	}
	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	//	@NotFound(action = NotFoundAction.IGNORE)
	public Actor getRecipient() {
		return this.recipient;
	}
	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

}
