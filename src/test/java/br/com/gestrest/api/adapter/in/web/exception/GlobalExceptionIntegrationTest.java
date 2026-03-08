package br.com.gestrest.api.adapter.in.web.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GlobalExceptionIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Validação DTO retorna ErrorResponse 400 com lista de erros")
    void validationErrorReturnsErrorResponse() throws Exception {
        String body = "{\n  \"nome\": \"Test User\",\n  \"email\": \"invalid-email\",\n  \"login\": \"test\",\n  \"senha\": \"123\",\n  \"tipoUsuarioId\": 1\n}";

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    @DisplayName("Criar item para restaurante inexistente retorna 404 com ErrorResponse")
    void createItemForNonexistentRestaurantReturns404() throws Exception {
        String body = "{\n  \"nome\": \"Fantasma\",\n  \"descricao\": \"Nao existe\",\n  \"preco\": 10.00,\n  \"restauranteId\": 99999\n}";

        mockMvc.perform(post("/api/v1/itens-cardapio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    @DisplayName("Criar usuário com email duplicado retorna 409 com ErrorResponse")
    void createUserWithDuplicateEmailReturns409() throws Exception {
        String tipoBody = "{\n  \"nome\": \"Admin\"\n}";

        MvcResult tipoResult = mockMvc.perform(post("/api/v1/tipos-usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tipoBody))
                .andExpect(status().isCreated())
                .andReturn();

        String tipoContent = tipoResult.getResponse().getContentAsString();
        JsonNode tipoJson = objectMapper.readTree(tipoContent);
        long tipoId = tipoJson.get("id").asLong();

        String usuario1 = String.format("{\n  \"nome\": \"User One\",\n  \"email\": \"dup@example.com\",\n  \"login\": \"userone\",\n  \"senha\": \"senha123\",\n  \"tipoUsuarioId\": %d\n}", tipoId);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuario1))
                .andExpect(status().isCreated());

        String usuario2 = String.format("{\n  \"nome\": \"User Two\",\n  \"email\": \"dup@example.com\",\n  \"login\": \"usertwo\",\n  \"senha\": \"senha123\",\n  \"tipoUsuarioId\": %d\n}", tipoId);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuario2))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"));
    }
}