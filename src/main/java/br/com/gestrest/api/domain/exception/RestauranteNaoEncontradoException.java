package br.com.gestrest.api.domain.exception;

public class RestauranteNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(Long id) {
        super("Restaurante não encontrado: " + id);
    }
}
