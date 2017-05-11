
package forms;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

public class RegisterUser {

	public RegisterUser() {
		super();
	}


	private String	username;
	private String	password;

	private boolean	accept;

	private String	name;
	private String	surname;
	private String	email;
	private String	phoneNumber;
	private String	postalAddress;
	private String	idNumber;


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
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotBlank
	public String getPostalAddress() {
		return this.postalAddress;
	}
	public void setPostalAddress(final String postalAddress) {
		this.postalAddress = postalAddress;
	}

	@NotBlank
	public String getIdNumber() {
		return this.idNumber;
	}
	public void setIdNumber(final String idNumber) {
		this.idNumber = idNumber;
	}


	//Relations

	private UserAccount	userAccount;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
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
		return this.accept;
	}
	public void setAccept(final boolean accept) {
		this.accept = accept;
	}

}
