package br.com.gestrest.api.domain.model.ports.in.tipousuario;

import br.com.gestrest.api.domain.model.TipoUsuario;

public interface BuscarTipoUsuarioPorIdUseCase {
	TipoUsuario buscarPorId(Long id);
}
