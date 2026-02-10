package br.com.gestrest.api.application.usecase.impl.usuario;

import br.com.gestrest.api.domain.model.ports.in.ExcluirUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExcluirUsuarioUseCaseImpl implements ExcluirUsuarioUseCase {

    private final UsuarioRepositoryPort repository;

    @Override
    public void executar(Long id) {
        repository.deletar(id);
    }
}
