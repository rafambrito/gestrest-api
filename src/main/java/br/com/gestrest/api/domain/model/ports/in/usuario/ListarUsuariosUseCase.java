package br.com.gestrest.api.domain.model.ports.in.usuario;

import br.com.gestrest.api.domain.model.Usuario;

public interface ListarUsuariosUseCase {
    java.util.List<Usuario> executar();
}
