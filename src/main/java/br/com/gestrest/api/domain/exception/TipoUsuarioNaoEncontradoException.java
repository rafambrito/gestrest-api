package br.com.gestrest.api.domain.exception;

public class TipoUsuarioNaoEncontradoException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public TipoUsuarioNaoEncontradoException(Long id) {
        super(id, "TipoUsuario");
    }
}