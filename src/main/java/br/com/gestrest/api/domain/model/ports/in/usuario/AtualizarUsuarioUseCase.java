package br.com.gestrest.api.domain.model.ports.in.usuario;

import br.com.gestrest.api.application.usecase.command.usuario.AtualizarUsuarioCommand;
import br.com.gestrest.api.domain.model.Usuario;

public interface AtualizarUsuarioUseCase {
    Usuario atualizar(AtualizarUsuarioCommand command);
}
