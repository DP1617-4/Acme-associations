
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Place extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Place() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String	address;
	private Double	latitude;
	private Double	longitude;


	@NotBlank
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	@Range(min = -85, max = 85)
	public Double getLatitude() {
		return this.latitude;
	}
	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	@Range(min = -180, max = 180)
	public Double getLongitude() {
		return this.longitude;
	}
	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

}
