package br.com.gestrest.api.adapter.in.web.exception;

public class TipoUsuarioNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public TipoUsuarioNaoEncontradoException(Long id) {
        super("Tipo de Usuario não encontrado: " + id);
    }
}