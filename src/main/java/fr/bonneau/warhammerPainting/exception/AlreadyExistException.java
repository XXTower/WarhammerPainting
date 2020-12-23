package fr.bonneau.warhammerPainting.exception;

public class AlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
