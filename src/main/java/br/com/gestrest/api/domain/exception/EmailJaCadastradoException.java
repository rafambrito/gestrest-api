package br.com.gestrest.api.domain.exception;

public class EmailJaCadastradoException extends DuplicateResourceException {
    private static final long serialVersionUID = 1L;

    public EmailJaCadastradoException(String message) {
        super(message);
    }

    public EmailJaCadastradoException() {
        super("Email já cadastrado");
    }
}
