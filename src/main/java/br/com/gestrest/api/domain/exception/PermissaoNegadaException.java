package br.com.gestrest.api.domain.exception;

public class PermissaoNegadaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PermissaoNegadaException(String message) {
        super(message);
    }
}
