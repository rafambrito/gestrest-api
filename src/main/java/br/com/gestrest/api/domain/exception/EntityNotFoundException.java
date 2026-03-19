package br.com.gestrest.api.domain.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Long id, String resourceName) {
        super(resourceName + " não encontrado: " + id);
    }
}
