
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class CreditHolder extends Actor {

	// Constructors -----------------------------------------------------------

	public CreditHolder() {
		super();
	}


	// Attributes -------------------------------------------------------------
	private Double	cumulatedFee;


	public Double getCumulatedFee() {
		return this.cumulatedFee;
	}
	public void setCumulatedFee(final Double cumulatedFee) {
		this.cumulatedFee = cumulatedFee;
	}

	// Relationships ----------------------------------------------------------

}
