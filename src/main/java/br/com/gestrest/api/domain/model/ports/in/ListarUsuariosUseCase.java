package br.com.gestrest.api.domain.model.ports.in;

import br.com.gestrest.api.domain.model.Usuario;

public interface ListarUsuariosUseCase {
    java.util.List<Usuario> executar();
}
