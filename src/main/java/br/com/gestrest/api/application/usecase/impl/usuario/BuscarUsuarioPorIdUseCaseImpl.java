package br.com.gestrest.api.application.usecase.impl.usuario;

import java.util.Optional;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.in.usuario.BuscarUsuarioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuscarUsuarioPorIdUseCaseImpl implements BuscarUsuarioPorIdUseCase {

    private final UsuarioRepositoryPort repository;

    @Override
    public Optional<Usuario> executar(Long id) {
        return repository.buscarPorId(id);
    }
}
