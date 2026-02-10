package br.com.gestrest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gestrest.api.application.usecase.impl.tipousuario.AtualizarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.tipousuario.BuscarTipoUsuarioPorIdUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.tipousuario.CriarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.tipousuario.ExcluirTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.tipousuario.ListarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.usuario.AtualizarUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.usuario.BuscarUsuarioPorIdUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.usuario.CriarUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.usuario.ExcluirUsuarioUseCaseImpl;
import br.com.gestrest.api.application.usecase.impl.usuario.ListarUsuariosUseCaseImpl;
import br.com.gestrest.api.domain.model.ports.in.AtualizarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.AtualizarUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.BuscarTipoUsuarioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.BuscarUsuarioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.CriarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.CriarUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.ExcluirTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.ExcluirUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.ListarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.ListarUsuariosUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@Configuration
public class UseCaseConfig {

	// TipoUsuario Use Cases
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

	// Usuario Use Cases
	@Bean
	public CriarUsuarioUseCase criarUsuarioUseCase(
			UsuarioRepositoryPort usuarioRepository,
			TipoUsuarioRepositoryPort tipoRepository) {
		return new CriarUsuarioUseCaseImpl(usuarioRepository, tipoRepository);
	}

	@Bean
	public ListarUsuariosUseCase listarUsuariosUseCase(UsuarioRepositoryPort repository) {
		return new ListarUsuariosUseCaseImpl(repository);
	}

	@Bean
	public BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase(UsuarioRepositoryPort repository) {
		return new BuscarUsuarioPorIdUseCaseImpl(repository);
	}

	@Bean
	public AtualizarUsuarioUseCase atualizarUsuarioUseCase(
			UsuarioRepositoryPort usuarioRepository,
			TipoUsuarioRepositoryPort tipoRepository) {
		return new AtualizarUsuarioUseCaseImpl(usuarioRepository, tipoRepository);
	}

	@Bean
	public ExcluirUsuarioUseCase excluirUsuarioUseCase(UsuarioRepositoryPort repository) {
		return new ExcluirUsuarioUseCaseImpl(repository);
	}
}
