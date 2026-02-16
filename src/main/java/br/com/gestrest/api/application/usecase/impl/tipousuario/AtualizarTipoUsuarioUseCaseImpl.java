package br.com.gestrest.api.application.usecase.impl.tipousuario;

import br.com.gestrest.api.adapter.in.web.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.AtualizarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

public class AtualizarTipoUsuarioUseCaseImpl implements AtualizarTipoUsuarioUseCase {

	private final TipoUsuarioRepositoryPort repository;

	public AtualizarTipoUsuarioUseCaseImpl(TipoUsuarioRepositoryPort repository) {
		this.repository = repository;
	}

	@Override
	public TipoUsuario atualizar(TipoUsuario tipoUsuario) {

		var existente = repository.buscarPorId(tipoUsuario.getId())
				.orElseThrow(() -> new TipoUsuarioNaoEncontradoException(tipoUsuario.getId()));

		existente.atualizarDados(tipoUsuario.getNome());

		return repository.salvar(existente);
	}
}