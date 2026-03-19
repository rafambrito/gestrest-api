package br.com.gestrest.api.domain.exception;

public class UnauthorizedOperationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedOperationException(String message) {
        super(message);
    }
}
