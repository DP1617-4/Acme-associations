
package forms;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Item;

public class ChangeCondition {

	public ChangeCondition() {
		super();
	}


	private Item	Item;
	private String	condition;


	@NotBlank
	public Item getItem() {
		return this.Item;
	}
	public void setItem(final Item Item) {
		this.Item = Item;
	}

	@NotNull
	@Valid
	public String getCondition() {
		return this.condition;
	}
	public void setCondition(final String condition) {
		this.condition = condition;
	}
}
