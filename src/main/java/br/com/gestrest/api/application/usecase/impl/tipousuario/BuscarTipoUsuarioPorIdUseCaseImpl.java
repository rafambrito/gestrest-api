package br.com.gestrest.api.application.usecase.impl.tipousuario;

import br.com.gestrest.api.adapter.in.web.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

public class BuscarTipoUsuarioPorIdUseCaseImpl implements BuscarTipoUsuarioPorIdUseCase {

	private final TipoUsuarioRepositoryPort repository;

	public BuscarTipoUsuarioPorIdUseCaseImpl(TipoUsuarioRepositoryPort repository) {
		this.repository = repository;
	}

	@Override
	public TipoUsuario buscarPorId(Long id) {

		return repository.buscarPorId(id).orElseThrow(() -> new TipoUsuarioNaoEncontradoException(id));
	}
}
