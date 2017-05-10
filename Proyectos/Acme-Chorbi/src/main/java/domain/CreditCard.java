
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public CreditCard() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	holderName;
	private String	brandName;
	private String	creditCardNumber;
	private int		expirationMonth;
	private int		expirationYear;
	private int		CVV;


	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	@Pattern(regexp = "^VISA|MASTERCARD|DISCOVER|DINNERS|AMEX$")
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@Column(length = 16)
	@NotBlank
	public String getCreditCardNumber() {
		return this.creditCardNumber;
	}

	public void setCreditCardNumber(final String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	@NotNull
	@Range(min = 1, max = 12)
	public int getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	@Range(min = 0, max = 99)
	public int getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public int getCVV() {
		return this.CVV;
	}

	public void setCVV(final int cVV) {
		this.CVV = cVV;
	}


	// Relationships ----------------------------------------------------------
	private CreditHolder	holder;


	@Valid
	@NotNull
	@OneToOne(optional = false)
	public CreditHolder getHolder() {
		return this.holder;
	}
	public void setHolder(final CreditHolder holder) {
		this.holder = holder;
	}
}
