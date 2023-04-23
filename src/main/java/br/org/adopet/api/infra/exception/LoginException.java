package br.org.adopet.api.infra.exception;

public class LoginException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Login ou Senha incorreto.";
	
	public LoginException() {
		super(MESSAGE);
	}
}
