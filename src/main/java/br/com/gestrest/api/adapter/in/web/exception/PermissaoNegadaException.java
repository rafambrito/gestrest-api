package br.com.gestrest.api.adapter.in.web.exception;

public class PermissaoNegadaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PermissaoNegadaException(String message) {
        super(message);
    }
}
