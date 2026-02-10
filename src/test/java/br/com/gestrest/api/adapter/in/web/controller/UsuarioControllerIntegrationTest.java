package br.com.gestrest.api.adapter.in.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarUsuarioRequest;
import br.com.gestrest.api.adapter.out.persistence.entity.TipoUsuarioEntity;
import br.com.gestrest.api.adapter.out.persistence.repository.TipoUsuarioJpaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("UsuarioController Integration Tests")
class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TipoUsuarioJpaRepository tipoUsuarioRepository;

    private Long tipoUsuarioId;

    @BeforeEach
    void setUp() {
        // Criar um tipo de usuário para testes
        var tipoEntity = new TipoUsuarioEntity();
        tipoEntity.setNome("Admin");
        var tipo = tipoUsuarioRepository.save(tipoEntity);
        tipoUsuarioId = tipo.getId();
    }

    @Test
    @DisplayName("POST /api/v1/usuarios - Deve criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() throws Exception {
        // Arrange
        var request = new CriarUsuarioRequest(
            "João Silva",
            "joao@example.com",
            "joao.silva",
            "senha123",
            "Rua A, 123",
            tipoUsuarioId
        );

        // Act & Assert
        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.nome", equalTo("João Silva")))
            .andExpect(jsonPath("$.email", equalTo("joao@example.com")))
            .andExpect(jsonPath("$.tipoUsuario.id", equalTo(tipoUsuarioId.intValue())));
    }

    @Test
    @DisplayName("POST /api/v1/usuarios - Deve falhar com email inválido")
    void devefalharComEmailInvalido() throws Exception {
        // Arrange
        var request = new CriarUsuarioRequest(
            "João Silva",
            "email-invalido",
            "joao.silva",
            "senha123",
            "Rua A, 123",
            tipoUsuarioId
        );

        // Act & Assert
        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/usuarios - Deve listar usuários")
    void deveListarUsuarios() throws Exception {
        // Arrange
        var request = new CriarUsuarioRequest(
            "Maria Silva",
            "maria@example.com",
            "maria.silva",
            "senha456",
            "Rua B, 456",
            tipoUsuarioId
        );

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        // Act & Assert
        mockMvc.perform(get("/api/v1/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
            .andExpect(jsonPath("$[0].nome", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/v1/usuarios/{id} - Deve buscar usuário por ID")
    void deveBuscarUsuarioPorId() throws Exception {
        // Arrange
        var request = new CriarUsuarioRequest(
            "Pedro Silva",
            "pedro@example.com",
            "pedro.silva",
            "senha789",
            "Rua C, 789",
            tipoUsuarioId
        );

        var response = mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();

        var content = response.getResponse().getContentAsString();
        var usuarioResponse = objectMapper.readTree(content);
        long usuarioId = usuarioResponse.get("id").asLong();

        // Act & Assert
        mockMvc.perform(get("/api/v1/usuarios/" + usuarioId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo((int)usuarioId)))
            .andExpect(jsonPath("$.nome", equalTo("Pedro Silva")))
            .andExpect(jsonPath("$.email", equalTo("pedro@example.com")));
    }

    @Test
    @DisplayName("PUT /api/v1/usuarios/{id} - Deve atualizar usuário")
    void deveAtualizarUsuario() throws Exception {
        // Arrange
        var criarRequest = new CriarUsuarioRequest(
            "Ana Silva",
            "ana@example.com",
            "ana.silva",
            "senha321",
            "Rua D, 321",
            tipoUsuarioId
        );

        var response = mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(criarRequest)))
            .andExpect(status().isCreated())
            .andReturn();

        var content = response.getResponse().getContentAsString();
        var usuarioResponse = objectMapper.readTree(content);
        var usuarioId = usuarioResponse.get("id").asLong();

        var atualizarRequest = new AtualizarUsuarioRequest(
            "Ana Santos",
            "ana.santos@example.com",
            "Rua E, 654",
            tipoUsuarioId
        );

        // Act & Assert
        mockMvc.perform(put("/api/v1/usuarios/" + usuarioId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atualizarRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome", equalTo("Ana Santos")))
            .andExpect(jsonPath("$.email", equalTo("ana.santos@example.com")));
    }

    @Test
    @DisplayName("DELETE /api/v1/usuarios/{id} - Deve deletar usuário")
    void deveDeletarUsuario() throws Exception {
        // Arrange
        var request = new CriarUsuarioRequest(
            "Carlos Silva",
            "carlos@example.com",
            "carlos.silva",
            "senha654",
            "Rua F, 987",
            tipoUsuarioId
        );

        var response = mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();

        var content = response.getResponse().getContentAsString();
        var usuarioResponse = objectMapper.readTree(content);
        var usuarioId = usuarioResponse.get("id").asLong();

        // Act & Assert
        mockMvc.perform(delete("/api/v1/usuarios/" + usuarioId))
            .andExpect(status().isNoContent());

        // Verificar que foi deletado
        mockMvc.perform(get("/api/v1/usuarios/" + usuarioId))
            .andExpect(status().isNotFound());
    }
}
