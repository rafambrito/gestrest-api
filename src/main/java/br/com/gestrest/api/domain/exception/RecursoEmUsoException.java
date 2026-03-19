package br.com.gestrest.api.domain.exception;

public class RecursoEmUsoException extends DuplicateResourceException {
    private static final long serialVersionUID = 1L;

    public RecursoEmUsoException(String message) {
        super(message);
    }
}