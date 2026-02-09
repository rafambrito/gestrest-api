package br.com.gestrest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gestrest.api.application.usecase.impl.tipousuario.AtualizarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.tipousuario.BuscarTipoUsuarioPorIdUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.tipousuario.CriarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.tipousuario.ExcluirTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.tipousuario.ListarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.domain.model.ports.in.AtualizarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.BuscarTipoUsuarioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.CriarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.ExcluirTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.ListarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

@Configuration
public class UseCaseConfig {

	@Bean
	public CriarTipoUsuarioUseCase criarTipoUsuarioUseCase(TipoUsuarioRepositoryPort repository) {
		return new CriarTipoUsuarioUseCaseImpl(repository);
	}

	@Bean
	public AtualizarTipoUsuarioUseCase atualizarTipoUsuarioUseCase(TipoUsuarioRepositoryPort repository) {
		return new AtualizarTipoUsuarioUseCaseImpl(repository);
	}

	@Bean
	public BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase(TipoUsuarioRepositoryPort repository) {
		return new BuscarTipoUsuarioPorIdUseCaseImpl(repository);
	}

	@Bean
	public ListarTipoUsuarioUseCase listarTipoUsuarioUseCase(TipoUsuarioRepositoryPort repository) {
		return new ListarTipoUsuarioUseCaseImpl(repository);
	}

	@Bean
	public ExcluirTipoUsuarioUseCase excluirTipoUsuarioUseCase(TipoUsuarioRepositoryPort repository) {
		return new ExcluirTipoUsuarioUseCaseImpl(repository);
	}
}
