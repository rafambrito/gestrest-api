package br.com.gestrest.api.domain.model.ports.in;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarUsuarioCommand;
import br.com.gestrest.api.domain.model.Usuario;

public interface CriarUsuarioUseCase {
	public Usuario criar(CriarUsuarioCommand command);
}
