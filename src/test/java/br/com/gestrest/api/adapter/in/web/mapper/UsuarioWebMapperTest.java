package br.com.gestrest.api.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.UsuarioResponse;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;

@DisplayName("UsuarioWebMapper Tests")
class UsuarioWebMapperTest {

    private UsuarioWebMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UsuarioWebMapper();
    }

    @Test
    @DisplayName("Deve mapear Usuario para UsuarioResponse")
    void deveMapearUsuarioParaUsuarioResponse() {
        // Arrange
        var tipoUsuario = TipoUsuario.existente(1L, "Admin");
        var usuario = Usuario.existente(
            1L,
            "João Silva",
            "joao@example.com",
            "joao.silva",
            "senha123",
            "Rua A, 123",
            tipoUsuario
        );

        // Act
        var response = mapper.toResponse(usuario);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("João Silva", response.nome());
        assertEquals("joao@example.com", response.email());
        assertEquals("joao.silva", response.login());
        assertNotNull(response.tipoUsuario());
        assertEquals(1L, response.tipoUsuario().id());
    }

    @Test
    @DisplayName("Deve retornar null ao mapear null")
    void deveRetornarNullAoMapearNull() {
        // Act
        var response = mapper.toResponse(null);

        // Assert
        assertNull(response);
    }

    @Test
    @DisplayName("Deve mapear CriarUsuarioRequest para CriarUsuarioCommand")
    void deveMapearCriarRequestParaCommand() {
        // Arrange
        var request = new CriarUsuarioRequest(
            "Maria Silva",
            "maria@example.com",
            "maria.silva",
            "senha456",
            "Rua B, 456",
            1L
        );

        // Act
        var command = mapper.toDomain(request);

        // Assert
        assertNotNull(command);
        assertEquals("Maria Silva", command.nome());
        assertEquals("maria@example.com", command.email());
        assertEquals("maria.silva", command.login());
        assertEquals("senha456", command.senha());
        assertEquals("Rua B, 456", command.endereco());
        assertEquals(1L, command.tipoUsuarioId());
    }

    @Test
    @DisplayName("Deve mapear AtualizarUsuarioRequest para AtualizarUsuarioCommand")
    void deveMapearAtualizarRequestParaCommand() {
        // Arrange
        var request = new AtualizarUsuarioRequest(
            "Pedro Santos",
            "pedro@example.com",
            "Rua C, 789",
            1L
        );

        // Act
        var command = mapper.toDomain(2L, request);

        // Assert
        assertNotNull(command);
        assertEquals(2L, command.id());
        assertEquals("Pedro Santos", command.nome());
        assertEquals("pedro@example.com", command.email());
        assertEquals("Rua C, 789", command.endereco());
        assertEquals(1L, command.tipoUsuarioId());
    }

    @Test
    @DisplayName("Deve retornar null ao mapear CriarRequest null")
    void deveRetornarNullAoMapearCriarRequestNull() {
        // Act
        var command = mapper.toDomain((CriarUsuarioRequest) null);

        // Assert
        assertNull(command);
    }

    @Test
    @DisplayName("Deve retornar null ao mapear AtualizarRequest null")
    void deveRetornarNullAoMapearAtualizarRequestNull() {
        // Act
        var command = mapper.toDomain(1L, null);

        // Assert
        assertNull(command);
    }
}