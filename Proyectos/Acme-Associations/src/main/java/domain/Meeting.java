
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "moment"), @Index(columnList = "association_id")
})
public class Meeting extends Commentable {

	// Constructors -----------------------------------------------------------

	public Meeting() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Date	moment;
	private String	address;
	private String	agenda;
	private String	issue;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	@URL
	public String getAgenda() {
		return this.agenda;
	}
	public void setAgenda(final String agenda) {
		this.agenda = agenda;
	}

	@NotBlank
	public String getIssue() {
		return this.issue;
	}
	public void setIssue(final String issue) {
		this.issue = issue;
	}


	// Relationships ----------------------------------------------------------

	private Association	association;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Association getAssociation() {
		return this.association;
	}
	public void setAssociation(final Association association) {
		this.association = association;
	}

}
