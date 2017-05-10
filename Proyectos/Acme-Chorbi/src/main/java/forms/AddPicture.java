
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class AddPicture {

	public AddPicture() {
		super();
	}


	private String	picture;
	private int		id;


	@URL
	@NotBlank
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public int getId() {

		return this.id;
	}

	public void setId(final int id) {

		this.id = id;
	}

}
