package br.com.gestrest.api.domain.model.ports.in.tipousuario;

import br.com.gestrest.api.domain.model.TipoUsuario;

public interface AtualizarTipoUsuarioUseCase {
	
    TipoUsuario atualizar(TipoUsuario tipoUsuario);
}