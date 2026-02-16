package br.com.gestrest.api.domain.model.ports.in.usuario;

import br.com.gestrest.api.application.usecase.command.usuario.CriarUsuarioCommand;
import br.com.gestrest.api.domain.model.Usuario;

public interface CriarUsuarioUseCase {
	public Usuario criar(CriarUsuarioCommand command);
}
