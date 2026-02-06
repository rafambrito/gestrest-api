package br.com.gestrest.api.application.usecase.tipousuario;

import java.util.List;

import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.in.ListarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

public class ListarTipoUsuarioUseCaseImpl implements ListarTipoUsuarioUseCase {

	private final TipoUsuarioRepositoryPort repository;

	public ListarTipoUsuarioUseCaseImpl(TipoUsuarioRepositoryPort repository) {
		this.repository = repository;
	}

	@Override
	public List<TipoUsuario> executar() {
		return repository.listar();
	}
}
