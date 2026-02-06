package br.com.gestrest.api.application.usecase.tipousuario;

import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.in.CriarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

public class CriarTipoUsuarioUseCaseImpl implements CriarTipoUsuarioUseCase {

    private final TipoUsuarioRepositoryPort repository;

    public CriarTipoUsuarioUseCaseImpl(TipoUsuarioRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public TipoUsuario executar(TipoUsuario tipoUsuario) {
        return repository.salvar(tipoUsuario);
    }
}

