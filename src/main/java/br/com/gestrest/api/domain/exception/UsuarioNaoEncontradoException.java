package br.com.gestrest.api.domain.exception;

public class UsuarioNaoEncontradoException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(Long id) {
        super(id, "Usuario");
    }
}