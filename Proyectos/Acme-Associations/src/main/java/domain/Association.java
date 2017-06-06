
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "adminClosed"), @Index(columnList = "closedAssociation")
})
public class Association extends Commentable {

	private String	name;
	private String	description;
	private String	address;
	private Date	creationDate;
	private String	statutes;
	private String	announcements;
	private String	picture;
	private Boolean	closedAssociation;
	private Boolean	adminClosed;


	// Constructors -----------------------------------------------------------

	public Association() {
		super();
	}

	// Attributes -------------------------------------------------------------

	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationDate() {
		return this.creationDate;
	}
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	@URL
	@NotBlank
	@SafeHtml
	public String getStatutes() {
		return this.statutes;
	}
	public void setStatutes(final String statutes) {
		this.statutes = statutes;
	}

	@SafeHtml
	public String getAnnouncements() {
		return this.announcements;
	}
	public void setAnnouncements(final String announcements) {
		this.announcements = announcements;
	}

	@URL
	@SafeHtml
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public Boolean getClosedAssociation() {
		return this.closedAssociation;
	}
	public void setClosedAssociation(final Boolean closedAssociation) {
		this.closedAssociation = closedAssociation;
	}

	public Boolean getAdminClosed() {
		return this.adminClosed;
	}
	public void setAdminClosed(final Boolean adminClosed) {
		this.adminClosed = adminClosed;
	}

	public void checkClosedOrBanned(final Association association) {

		Assert.isTrue(association.getAdminClosed() == true || association.getClosedAssociation() == true, "association.closed.error");
	}

	// Relationships ----------------------------------------------------------

}
