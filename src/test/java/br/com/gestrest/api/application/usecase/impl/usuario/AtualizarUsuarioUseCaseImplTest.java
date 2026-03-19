package br.com.gestrest.api.application.usecase.impl.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.application.usecase.command.usuario.AtualizarUsuarioCommand;
import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.exception.UsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("AtualizarUsuarioUseCaseImpl Testes")
class AtualizarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private TipoUsuarioRepositoryPort tipoRepository;

    @InjectMocks
    private AtualizarUsuarioUseCaseImpl useCase;

    @Test
    void atualizarSucesso() {
        var tipo = TipoUsuario.existente(1L, "CLIENTE");
        var dataCriacao = LocalDateTime.of(2025, 1, 10, 12, 0);
        var usuarioExistente = Usuario.existente(2L, "Rafael Brito", "rafael.brito@gestrest.com", "rafael.brito", "Senha@321", "Rua das Rosas, São Paulo/SP", tipo,
                dataCriacao, null);

        var command = new AtualizarUsuarioCommand(2L, "Rafael Brito", "rafael.brito@gestrest.com", "Rua das Rosas, São Paulo/SP", 1L);

        when(usuarioRepository.buscarPorId(2L)).thenReturn(Optional.of(usuarioExistente));
        when(tipoRepository.buscarPorId(1L)).thenReturn(Optional.of(tipo));
        when(usuarioRepository.salvar(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = useCase.atualizar(command);

        assertEquals("Rafael Brito", result.getNome());
        assertEquals("rafael.brito@gestrest.com", result.getEmail());
        assertNotNull(result.getDataUltimaAlteracao(), "dataUltimaAlteracao deve ser preenchida após atualizar");
        assertEquals(dataCriacao, result.getDataCriacao(), "dataCriacao não deve ser alterada pelo atualizar");
    }

    @Test
    void usuarioNaoEncontrado() {
        var command = new AtualizarUsuarioCommand(99L, "Rafael Brito", "rafael.brito@gestrest.com", "Rua das Rosas, São Paulo/SP", 1L);
        when(usuarioRepository.buscarPorId(99L)).thenReturn(Optional.empty());
        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.atualizar(command));
    }

    @Test
    void tipoNaoEncontrado() {
        var usuarioExistente = Usuario.existente(3L, "José Pereira", "jose.pereira@gestrest.com", "jose.pereira", "Senha@445", "Rua das Rosas, São Paulo/SP", TipoUsuario.existente(9L, "OPERADOR_CAIXA"));
        var command = new AtualizarUsuarioCommand(3L, "José Pereira", "jose.pereira@gestrest.com", "Rua das Rosas, São Paulo/SP", 999L);
        when(usuarioRepository.buscarPorId(3L)).thenReturn(Optional.of(usuarioExistente));
        when(tipoRepository.buscarPorId(999L)).thenReturn(Optional.empty());
        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.atualizar(command));
    }
}
