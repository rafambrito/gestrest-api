package br.com.gestrest.api.domain.model.ports.in;

import br.com.gestrest.api.domain.model.TipoUsuario;

public interface BuscarTipoUsuarioPorIdUseCase {
	TipoUsuario executar(Long id);
}
