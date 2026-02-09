package br.com.gestrest.api.application.usecase.impl.usuario;

import br.com.gestrest.api.application.usecase.usuario.command.CriarUsuarioCommand;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.in.CriarUsuarioUseCase;
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
                .orElseThrow(() ->
                        new RuntimeException("TipoUsuario não encontrado"));

        var usuario = Usuario.criar(
                command.nome(),
                command.email(),
                command.login(),
                command.senha(),
                command.endereco(),
                tipo
        );

        return usuarioRepository.salvar(usuario);
    }
}