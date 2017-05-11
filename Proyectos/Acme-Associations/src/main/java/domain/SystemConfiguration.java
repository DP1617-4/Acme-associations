
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfiguration extends DomainEntity {

	//Constructor

	public SystemConfiguration() {
		super();
	}


	//Attributes

	private Collection<String>	banners;
	private Date				cacheTime;
	private Double				feeManager;
	private Double				feeChorbi;


	@ElementCollection
	@NotNull
	public Collection<String> getBanners() {
		return this.banners;
	}
	public void setBanners(final Collection<String> banners) {
		this.banners = banners;
	}

	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm:ss")
	@NotNull
	public Date getCacheTime() {
		return this.cacheTime;
	}
	public void setCacheTime(final Date cacheTime) {
		this.cacheTime = cacheTime;
	}

	@Min(0)
	@NotNull
	public Double getFeeManager() {
		return this.feeManager;
	}
	public void setFeeManager(final Double feeManager) {
		this.feeManager = feeManager;
	}

	@Min(0)
	@NotNull
	public Double getFeeChorbi() {
		return this.feeChorbi;
	}
	public void setFeeChorbi(final Double feeChorbi) {
		this.feeChorbi = feeChorbi;
	}

}
