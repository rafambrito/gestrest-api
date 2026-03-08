package br.com.gestrest.api.adapter.in.web.exception;

public class RestauranteNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(Long id) {
        super("Restaurante não encontrado: " + id);
    }
}
