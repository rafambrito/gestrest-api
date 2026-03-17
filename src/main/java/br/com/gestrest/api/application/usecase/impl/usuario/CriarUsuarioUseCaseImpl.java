package br.com.gestrest.api.application.usecase.impl.usuario;

import br.com.gestrest.api.application.usecase.command.usuario.CriarUsuarioCommand;
import br.com.gestrest.api.domain.exception.EmailJaCadastradoException;
import br.com.gestrest.api.domain.exception.LoginJaCadastradoException;
import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.in.usuario.CriarUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

public class CriarUsuarioUseCaseImpl implements CriarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final TipoUsuarioRepositoryPort tipoRepository;

    public CriarUsuarioUseCaseImpl(
            UsuarioRepositoryPort usuarioRepository,
            TipoUsuarioRepositoryPort tipoRepository) {

        this.usuarioRepository = usuarioRepository;
        this.tipoRepository = tipoRepository;
    }

    @Override
    public Usuario criar(CriarUsuarioCommand command) {

        var tipo = tipoRepository.buscarPorId(command.tipoUsuarioId())
                .orElseThrow(() -> new TipoUsuarioNaoEncontradoException(command.tipoUsuarioId()));

        var email = command.email();
        var login = command.login();

        if (email != null && usuarioRepository.buscarPorEmail(email).isPresent()) {
            throw new EmailJaCadastradoException();
        }

        if (login != null && usuarioRepository.buscarPorLogin(login).isPresent()) {
            throw new LoginJaCadastradoException();
        }

        var usuario = Usuario.criar(
                command.nome(),
                email,
                login,
                command.senha(),
                command.endereco(),
                tipo
        );

        return usuarioRepository.salvar(usuario);
    }
}