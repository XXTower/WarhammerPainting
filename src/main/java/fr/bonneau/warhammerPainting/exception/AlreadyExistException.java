package fr.bonneau.warhammerPainting.exception;

public class AlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	private String object;
	
	public AlreadyExistException(String object) {
		this.object = object;
	}

	@Override
	public String getMessage() {
		return String.format("this %s already existe", object);
	}
}
