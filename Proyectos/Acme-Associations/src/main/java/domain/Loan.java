
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Loan extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Loan() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private Date	startDate;
	private Date	expectedDate;
	private Date	finalDate;


	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getExpectedDate() {
		return this.expectedDate;
	}
	public void setExpectedDate(final Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getFinalDate() {
		return this.finalDate;
	}
	public void setFinalDate(final Date finalDate) {
		this.finalDate = finalDate;
	}


	// Relationships ----------------------------------------------------------

	private Item	item;
	private User	lender;
	private User	borrower;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Item getItem() {
		return this.item;
	}
	public void setItem(final Item item) {
		this.item = item;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getLender() {
		return this.lender;
	}
	public void setLender(final User lender) {
		this.lender = lender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getBorrower() {
		return this.borrower;
	}
	public void setBorrower(final User borrower) {
		this.borrower = borrower;
	}

}
