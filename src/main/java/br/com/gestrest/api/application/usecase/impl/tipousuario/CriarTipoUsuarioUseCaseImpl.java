package br.com.gestrest.api.application.usecase.impl.tipousuario;

import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.CriarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

public class CriarTipoUsuarioUseCaseImpl implements CriarTipoUsuarioUseCase {

    private final TipoUsuarioRepositoryPort repository;

    public CriarTipoUsuarioUseCaseImpl(TipoUsuarioRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public TipoUsuario criar(TipoUsuario tipoUsuario) {
        return repository.salvar(tipoUsuario);
    }
}

