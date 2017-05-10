
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
	"chorbi_id", "liked_id"
}))
public class Likes extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Likes() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Date	moment;
	private String	comment;
	private Integer	stars;


	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Min(0)
	@Max(3)
	public Integer getStars() {
		return this.stars;
	}
	public void setStars(final Integer stars) {
		this.stars = stars;
	}


	// Relationships ----------------------------------------------------------
	private Chorbi	chorbi;
	private Chorbi	liked;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getChorbi() {
		return this.chorbi;
	}
	public void setChorbi(final Chorbi chorbi) {
		this.chorbi = chorbi;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chorbi getLiked() {
		return this.liked;
	}
	public void setLiked(final Chorbi liked) {
		this.liked = liked;
	}

}
