package br.com.gestrest.api.application.usecase.impl.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.application.usecase.command.usuario.CriarUsuarioCommand;
import br.com.gestrest.api.domain.exception.EmailJaCadastradoException;
import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.exception.LoginJaCadastradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("CriarUsuarioUseCaseImpl Tests")
class CriarUsuarioUseCaseImplTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @Mock
    private TipoUsuarioRepositoryPort tipoRepository;

    @InjectMocks
    private CriarUsuarioUseCaseImpl useCase;

    @Test
    @DisplayName("Deve criar usuario com sucesso")
    void deveCriarUsuarioComSucesso() {
        // Arrange
        var tipoUsuario = TipoUsuario.existente(1L, "Admin");
        var command = new CriarUsuarioCommand(
            "João Silva",
            "joao@example.com",
            "joao.silva",
            "senha123",
            "Rua A, 123",
            1L
        );

        when(tipoRepository.buscarPorId(1L)).thenReturn(Optional.of(tipoUsuario));
        
        var usuarioEsperado = Usuario.existente(
            1L,
            "João Silva",
            "joao@example.com",
            "joao.silva",
            "senha123",
            "Rua A, 123",
            tipoUsuario
        );
        when(usuarioRepository.salvar(any(Usuario.class))).thenReturn(usuarioEsperado);

        // Act
        var resultado = useCase.criar(command);

        // Assert
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@example.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Deve lancar excecao quando TipoUsuario nao encontrado")
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        // Arrange
        var command = new CriarUsuarioCommand(
            "João Silva",
            "joao@example.com",
            "joao.silva",
            "senha123",
            "Rua A, 123",
            999L
        );

        when(tipoRepository.buscarPorId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.criar(command));
    }

    @Test
    @DisplayName("Deve falhar quando email já existe")
    void deveFalharQuandoEmailDuplicado() {
        // Arrange
        var tipoUsuario = TipoUsuario.existente(1L, "Admin");
        var command = new CriarUsuarioCommand(
            "João Silva",
            "joao@example.com",
            "joao.silva",
            "senha123",
            "Rua A, 123",
            1L
        );

        when(tipoRepository.buscarPorId(1L)).thenReturn(Optional.of(tipoUsuario));
        when(usuarioRepository.buscarPorEmail("joao@example.com")).thenReturn(Optional.of(
            Usuario.existente(2L, "Outro", "joao@example.com", "outro", "senha", "end", tipoUsuario)
        ));

        // Act & Assert
        assertThrows(EmailJaCadastradoException.class, () -> useCase.criar(command));
    }

    @Test
    @DisplayName("Deve falhar quando login já existe")
    void deveFalharQuandoLoginDuplicado() {
        // Arrange
        var tipoUsuario = TipoUsuario.existente(1L, "Admin");
        var command = new CriarUsuarioCommand(
            "João Silva",
            "joao@example.com",
            "joao.silva",
            "senha123",
            "Rua A, 123",
            1L
        );

        when(tipoRepository.buscarPorId(1L)).thenReturn(Optional.of(tipoUsuario));
        when(usuarioRepository.buscarPorEmail("joao@example.com")).thenReturn(Optional.empty());
        when(usuarioRepository.buscarPorLogin("joao.silva")).thenReturn(Optional.of(
            Usuario.existente(3L, "Outro", "outro@example.com", "joao.silva", "senha", "end", tipoUsuario)
        ));

        // Act & Assert
        assertThrows(LoginJaCadastradoException.class, () -> useCase.criar(command));
    }
}