package br.com.gestrest.api.domain.model.ports.in.usuario;

import java.util.Optional;
import br.com.gestrest.api.domain.model.Usuario;

public interface BuscarUsuarioPorIdUseCase {
    Optional<Usuario> executar(Long id);
}
