package br.com.gestrest.api.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.RestauranteResponse;
import br.com.gestrest.api.adapter.in.web.mapper.RestauranteWebMapper;
import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.in.restaurante.AtualizarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.in.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.restaurante.CriarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.in.restaurante.ExcluirRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.in.restaurante.ListarRestauranteUseCase;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestauranteController.class)
class RestauranteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarRestauranteUseCase criar;

    @MockBean
    private AtualizarRestauranteUseCase atualizar;

    @MockBean
    private BuscarRestaurantePorIdUseCase buscar;

    @MockBean
    private ListarRestauranteUseCase listar;

    @MockBean
    private ExcluirRestauranteUseCase excluir;

    @MockBean
    private RestauranteWebMapper mapper;

    @Test
    void criar_sucesso() throws Exception {
        var req = new CriarRestauranteRequest("Nome", "End", "Coz", "Horario", 5L);
        var domain = Restaurante.criar("Nome", "End", "Coz", "Horario", 5L);
        var criado = Restaurante.existente(10L, "Nome", "End", "Coz", "Horario", 5L);
        var response = new RestauranteResponse(10L, "Nome", "End", "Coz", "Horario", 5L);

        when(mapper.toDomain(any(CriarRestauranteRequest.class))).thenReturn(domain);
        when(criar.criar(any())).thenReturn(criado);
        when(mapper.toResponse(criado)).thenReturn(response);

        mvc.perform(post("/api/v1/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/restaurantes/10"))
                .andExpect(jsonPath("$.nome").value("Nome"))
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void buscar_sucesso() throws Exception {
        var domain = Restaurante.existente(2L, "N", "E", "T", "H", 1L);
        var response = new RestauranteResponse(2L, "N", "E", "T", "H", 1L);

        when(buscar.executar(2L)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(response);

        mvc.perform(get("/api/v1/restaurantes/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nome").value("N"));
    }

    @Test
    void buscar_nao_encontrado() throws Exception {
        when(buscar.executar(eq(99L))).thenThrow(new RestauranteNaoEncontradoException(99L));

        mvc.perform(get("/api/v1/restaurantes/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
