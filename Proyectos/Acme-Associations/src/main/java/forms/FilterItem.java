
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class FilterItem{

	public FilterItem() {
		super();
	}


	private String text;
	

	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}

}