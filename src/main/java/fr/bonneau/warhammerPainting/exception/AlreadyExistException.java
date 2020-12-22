package fr.bonneau.warhammerPainting.exception;

public class AlreadyExistException extends Exception {

	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
