package br.com.gestrest.api.application.usecase.impl.tipousuario;

import br.com.gestrest.api.domain.model.ports.in.ExcluirTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

public class ExcluirTipoUsuarioUseCaseImpl implements ExcluirTipoUsuarioUseCase {

	private final TipoUsuarioRepositoryPort repository;

	public ExcluirTipoUsuarioUseCaseImpl(TipoUsuarioRepositoryPort repository) {
		this.repository = repository;
	}

	@Override
	public void deletar(Long id) {

		repository.buscarPorId(id).orElseThrow(() -> new RuntimeException("Tipo de Usuario não encontrado"));

		repository.deletar(id);
	}
}