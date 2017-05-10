
package forms;

public class ResendChirp {

	public ResendChirp() {
		super();
	}


	private int	chirpId;
	private int	recipientId;


	public int getChirpId() {
		return this.chirpId;
	}

	public void setChirpId(final int chirpId) {
		this.chirpId = chirpId;
	}

	public int getRecipientId() {

		return this.recipientId;
	}
	public void setRecipientId(final int recipientId) {

		this.recipientId = recipientId;
	}

}
