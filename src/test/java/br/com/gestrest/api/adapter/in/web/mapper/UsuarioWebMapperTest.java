package br.com.gestrest.api.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

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
        var tipoUsuario = TipoUsuario.existente(1L, "GERENTE_RESTAURANTE");
        var usuario = Usuario.existente(
            1L,
            "João da Silva",
            "joao.silva@gestrest.com",
            "joao.silva",
            "Senha@123",
            "Rua das Rosas, São Paulo/SP",
            tipoUsuario
        );

        // Act
        var response = mapper.toResponse(usuario);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("João da Silva", response.nome());
        assertEquals("joao.silva@gestrest.com", response.email());
        assertEquals("joao.silva", response.login());
        assertNotNull(response.tipoUsuario());
        assertEquals(1L, response.tipoUsuario().id());
    }

    @Test
    @DisplayName("Deve mapear dataCriacao e dataUltimaAlteracao para UsuarioResponse")
    void deveMapearCamposDeDataParaUsuarioResponse() {
        // Arrange
        var dataCriacao = LocalDateTime.of(2025, 1, 10, 12, 0);
        var dataUltimaAlteracao = LocalDateTime.of(2025, 6, 15, 8, 30);
        var tipoUsuario = TipoUsuario.existente(1L, "GERENTE_RESTAURANTE");
        var usuario = Usuario.existente(
            2L,
            "José Pereira",
            "jose.pereira@gestrest.com",
            "jose.pereira",
            "Senha@456",
            "Avenida Beija Flor, São Paulo/SP",
            tipoUsuario,
            dataCriacao,
            dataUltimaAlteracao
        );

        // Act
        var response = mapper.toResponse(usuario);

        // Assert
        assertNotNull(response);
        assertEquals(dataCriacao, response.dataCriacao());
        assertEquals(dataUltimaAlteracao, response.dataUltimaAlteracao());
    }

    @Test
    @DisplayName("Deve expor dataCriacao quando dataUltimaAlteracao é nula")
    void deveExporDataCriacaoComDataUltimaAlteracaoNula() {
        // Arrange
        var dataCriacao = LocalDateTime.of(2025, 3, 1, 10, 0);
        var tipoUsuario = TipoUsuario.existente(1L, "GERENTE_RESTAURANTE");
        var usuario = Usuario.existente(
            3L, "Rafael Brito", "rafael.brito@gestrest.com", "rafael.brito", "Senha@123", "Rua das Rosas, São Paulo/SP",
            tipoUsuario, dataCriacao, null
        );

        // Act
        var response = mapper.toResponse(usuario);

        // Assert
        assertEquals(dataCriacao, response.dataCriacao());
        assertNull(response.dataUltimaAlteracao());
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
            "José Pereira",
            "jose.pereira@gestrest.com",
            "jose.pereira",
            "Senha@456",
            "Avenida Beija Flor, São Paulo/SP",
            1L
        );

        // Act
        var command = mapper.toDomain(request);

        // Assert
        assertNotNull(command);
        assertEquals("José Pereira", command.nome());
        assertEquals("jose.pereira@gestrest.com", command.email());
        assertEquals("jose.pereira", command.login());
        assertEquals("Senha@456", command.senha());
        assertEquals("Avenida Beija Flor, São Paulo/SP", command.endereco());
        assertEquals(1L, command.tipoUsuarioId());
    }

    @Test
    @DisplayName("Deve mapear AtualizarUsuarioRequest para AtualizarUsuarioCommand")
    void deveMapearAtualizarRequestParaCommand() {
        // Arrange
        var request = new AtualizarUsuarioRequest(
            "José Pereira",
            "pedro.santos@gestrest.com",
            "Rua das Rosas, São Paulo/SP",
            1L
        );

        // Act
        var command = mapper.toDomain(2L, request);

        // Assert
        assertNotNull(command);
        assertEquals(2L, command.id());
        assertEquals("José Pereira", command.nome());
        assertEquals("pedro.santos@gestrest.com", command.email());
        assertEquals("Rua das Rosas, São Paulo/SP", command.endereco());
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