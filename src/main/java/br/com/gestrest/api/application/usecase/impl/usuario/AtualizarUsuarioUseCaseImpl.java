package br.com.gestrest.api.application.usecase.impl.usuario;

import br.com.gestrest.api.application.usecase.command.usuario.AtualizarUsuarioCommand;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.in.usuario.AtualizarUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AtualizarUsuarioUseCaseImpl implements AtualizarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final TipoUsuarioRepositoryPort tipoRepository;

    @Override
    public Usuario atualizar(AtualizarUsuarioCommand command) {
        var usuario = usuarioRepository.buscarPorId(command.id())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        var tipo = tipoRepository.buscarPorId(command.tipoUsuarioId())
            .orElseThrow(() -> new RuntimeException("TipoUsuario não encontrado"));
        
        usuario.setNome(command.nome());
        usuario.setEmail(command.email());
        usuario.setEndereco(command.endereco());
        usuario.setTipoUsuario(tipo);
        
        return usuarioRepository.salvar(usuario);
    }
}
