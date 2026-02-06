package br.com.gestrest.api.domain.model.ports.in;

import br.com.gestrest.api.domain.model.TipoUsuario;

public interface AtualizarTipoUsuarioUseCase {
	
    TipoUsuario executar(TipoUsuario tipoUsuario);
}