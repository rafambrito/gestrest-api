package br.com.gestrest.api.domain.model.ports.in;

import br.com.gestrest.api.application.usecase.usuario.command.AtualizarUsuarioCommand;
import br.com.gestrest.api.domain.model.Usuario;

public interface AtualizarUsuarioUseCase {
    Usuario executar(AtualizarUsuarioCommand command);
}
