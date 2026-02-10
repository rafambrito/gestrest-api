package br.com.gestrest.api.application.usecase.impl.usuario;

import java.util.List;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.in.ListarUsuariosUseCase;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListarUsuariosUseCaseImpl implements ListarUsuariosUseCase {

    private final UsuarioRepositoryPort repository;

    @Override
    public List<Usuario> executar() {
        return repository.listar();
    }
}
