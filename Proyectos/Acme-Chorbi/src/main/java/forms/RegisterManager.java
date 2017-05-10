package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class RegisterManager {
	
	public RegisterManager(){
		super();
	}
	
	private String	name;
	private String	surname;
	private String	email;
	private String	phoneNumber;
	
	private String	holderName;
	private String	brandName;
	private String	creditCardNumber;
	private int		expirationMonth;
	private int		expirationYear;
	private int		CVV;
	
	private String	companyName;
	private String	VATNumber;
	
	private String username;
	private String password;
	
	private boolean accept;

	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	@Pattern(regexp = "^([+](9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1))?\\d{1,14}$")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
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
	
	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}
	@NotBlank
	public String getVATNumber() {
		return this.VATNumber;
	}
	public void setVATNumber(final String VATNumber) {
		this.VATNumber = VATNumber;
	}
	
	@Size(min = 5, max = 32)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
	
	public boolean isAccept() {
		return accept;
	}
	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	
	
	
}
