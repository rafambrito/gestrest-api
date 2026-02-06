package br.com.gestrest.api.domain.model.ports.in;

import java.util.List;

import br.com.gestrest.api.domain.model.TipoUsuario;

public interface ListarTipoUsuarioUseCase {
	 List<TipoUsuario> executar();
}
