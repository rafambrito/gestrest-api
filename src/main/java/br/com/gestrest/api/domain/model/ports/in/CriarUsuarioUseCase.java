package br.com.gestrest.api.domain.model.ports.in;

import br.com.gestrest.api.application.usecase.usuario.command.CriarUsuarioCommand;
import br.com.gestrest.api.domain.model.Usuario;

public interface CriarUsuarioUseCase {
	public Usuario criar(CriarUsuarioCommand command);
}
