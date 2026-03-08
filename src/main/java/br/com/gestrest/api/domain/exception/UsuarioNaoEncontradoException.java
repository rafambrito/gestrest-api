package br.com.gestrest.api.domain.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(Long id) {
        super("Usuario não encontrado: " + id);
    }
}
