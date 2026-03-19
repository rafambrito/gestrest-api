package br.com.gestrest.api.application.usecase.impl.tipousuario;

import br.com.gestrest.api.domain.model.ports.in.tipousuario.ExcluirTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;
import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;

public class ExcluirTipoUsuarioUseCaseImpl implements ExcluirTipoUsuarioUseCase {

	private final TipoUsuarioRepositoryPort repository;

	public ExcluirTipoUsuarioUseCaseImpl(TipoUsuarioRepositoryPort repository) {
		this.repository = repository;
	}

	@Override
	public void deletar(Long id) {

		repository.buscarPorId(id).orElseThrow(() -> new TipoUsuarioNaoEncontradoException(id));

		repository.deletar(id);
	}
}