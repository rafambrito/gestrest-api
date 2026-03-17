package br.com.gestrest.api.application.usecase.impl.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.application.usecase.command.usuario.CriarUsuarioCommand;
import br.com.gestrest.api.domain.exception.EmailJaCadastradoException;
import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CriarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private TipoUsuarioRepositoryPort tipoRepository;

    private CriarUsuarioUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        useCase = new CriarUsuarioUseCaseImpl(usuarioRepository, tipoRepository);
    }

    @Test
    void criar_sucesso() {
        var tipo = TipoUsuario.existente(1L, "CLIENTE");
        var cmd = new CriarUsuarioCommand("Nome", "e@x.com", "login", "senha", "end", 1L);

        when(tipoRepository.buscarPorId(1L)).thenReturn(Optional.of(tipo));
        when(usuarioRepository.buscarPorEmail("e@x.com")).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorLogin("login")).thenReturn(Optional.empty());
        when(usuarioRepository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        Usuario u = useCase.criar(cmd);
        assertEquals("Nome", u.getNome());
        assertEquals("e@x.com", u.getEmail());
        assertEquals("login", u.getLogin());
        assertEquals(tipo, u.getTipoUsuario());
    }

    @Test
    void criar_email_duplicado() {
        var tipo = TipoUsuario.existente(1L, "CLIENTE");
        var cmd = new CriarUsuarioCommand("Nome", "e@x.com", "login", "senha", "end", 1L);

        when(tipoRepository.buscarPorId(1L)).thenReturn(Optional.of(tipo));
        when(usuarioRepository.buscarPorEmail("e@x.com")).thenReturn(Optional.of(Usuario.criar("x", "e@x.com", null, "s", "e", tipo)));

        assertThrows(EmailJaCadastradoException.class, () -> useCase.criar(cmd));
    }

    @Test
    void criar_tipo_inexistente() {
        var cmd = new CriarUsuarioCommand("Nome", "e@x.com", "login", "senha", "end", 99L);
        when(tipoRepository.buscarPorId(99L)).thenReturn(Optional.empty());
        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.criar(cmd));
    }
}
