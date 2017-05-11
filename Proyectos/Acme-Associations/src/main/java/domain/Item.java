
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Item extends Commentable {

	// Constructors -----------------------------------------------------------

	public Item() {
		super();
	}


	// Values -----------------------------------------------------------------

	public static final String	EXCELENT	= "EXCELENT";
	public static final String	GOOD		= "GOOD";
	public static final String	MODERATE	= "MODERATE";
	public static final String	BAD			= "BAD";
	public static final String	PRIZE		= "PRIZE";
	public static final String	LOAN		= "LOAN";

	// Attributes -------------------------------------------------------------

	private String				name;
	private String				identifier;
	private String				condition;
	private String				description;
	private String				picture;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getIdentifier() {
		return this.identifier;
	}
	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	@NotBlank
	@Pattern(regexp = "^" + Item.BAD + "|" + Item.EXCELENT + "|" + Item.GOOD + Item.LOAN + "|" + Item.MODERATE + "|" + Item.PRIZE + "$")
	public String getCondition() {
		return this.condition;
	}
	public void setCondition(final String condition) {
		this.condition = condition;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}


	// Relationships ----------------------------------------------------------

	private Section	belongsTo;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Section getBelongsTo() {
		return this.belongsTo;
	}
	public void setBelongsTo(final Section belongsTo) {
		this.belongsTo = belongsTo;
	}

}
