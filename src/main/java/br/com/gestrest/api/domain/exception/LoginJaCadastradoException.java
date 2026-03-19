package br.com.gestrest.api.domain.exception;

public class LoginJaCadastradoException extends DuplicateResourceException {
    private static final long serialVersionUID = 1L;

    public LoginJaCadastradoException(String message) {
        super(message);
    }

    public LoginJaCadastradoException() {
        super("Login já cadastrado");
    }
}
