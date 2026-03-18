package br.com.gestrest.api.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarRestauranteRequest;
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

    @MockitoBean
    private CriarRestauranteUseCase criar;

    @MockitoBean
    private AtualizarRestauranteUseCase atualizar;

    @MockitoBean
    private BuscarRestaurantePorIdUseCase buscar;

    @MockitoBean
    private ListarRestauranteUseCase listar;

    @MockitoBean
    private ExcluirRestauranteUseCase excluir;

    @MockitoBean
    private RestauranteWebMapper mapper;

    @Test
    void criar_sucesso() throws Exception {
        var req = new CriarRestauranteRequest("João da Silva", "Avenida Paulista, 1578 - Bela Vista, Sao Paulo/SP", "Contemporanea", "Seg-Sab 11:30-23:00", 5L);
        var domain = Restaurante.criar("João da Silva", "Avenida Paulista, 1578 - Bela Vista, Sao Paulo/SP", "Contemporanea", "Seg-Sab 11:30-23:00", 5L);
        var criado = Restaurante.existente(10L, "João da Silva", "Avenida Paulista, 1578 - Bela Vista, Sao Paulo/SP", "Contemporanea", "Seg-Sab 11:30-23:00", 5L);
        var response = new RestauranteResponse(10L, "João da Silva", "Avenida Paulista, 1578 - Bela Vista, Sao Paulo/SP", "Contemporanea", "Seg-Sab 11:30-23:00", 5L);

        when(mapper.toDomain(any(CriarRestauranteRequest.class))).thenReturn(domain);
        when(criar.criar(any())).thenReturn(criado);
        when(mapper.toResponse(criado)).thenReturn(response);

        mvc.perform(post("/api/v1/restaurantes")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(req))))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/restaurantes/10"))
            .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void buscar_sucesso() throws Exception {
        var domain = Restaurante.existente(2L, "José Pereira", "Rua Treze de Maio, 902 - Bela Vista, Sao Paulo/SP", "Italiana", "Ter-Dom 12:00-23:30", 1L);
        var response = new RestauranteResponse(2L, "José Pereira", "Rua Treze de Maio, 902 - Bela Vista, Sao Paulo/SP", "Italiana", "Ter-Dom 12:00-23:30", 1L);

        when(buscar.executar(2L)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(response);

        mvc.perform(get("/api/v1/restaurantes/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.nome").value("José Pereira"));
    }

    @Test
    void buscar_nao_encontrado() throws Exception {
        when(buscar.executar(eq(99L))).thenThrow(new RestauranteNaoEncontradoException(99L));

        mvc.perform(get("/api/v1/restaurantes/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_sem_dono_retorna_bad_request() throws Exception {
        String body = """
                {
                  \"nome\": \"Fiap Gourmet 2\",
                  \"endereco\": \"Av. Paulista, 1100\",
                  \"tipoCozinha\": \"Italiana\",
                  \"horarioFuncionamento\": \"11:00 - 23:00\"
                }
                """;

        mvc.perform(put("/api/v1/restaurantes/3")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("donoId"));
    }

    @Test
    void atualizar_com_dono_retorna_sucesso() throws Exception {
        var req = new AtualizarRestauranteRequest("Fiap Gourmet 2", "Av. Paulista, 1100", "Italiana", "11:00 - 23:00", 3L);
        var domain = Restaurante.existente(3L, "Fiap Gourmet 2", "Av. Paulista, 1100", "Italiana", "11:00 - 23:00", 3L);
        var response = new RestauranteResponse(3L, "Fiap Gourmet 2", "Av. Paulista, 1100", "Italiana", "11:00 - 23:00", 3L);

        when(mapper.toDomain(eq(3L), any(AtualizarRestauranteRequest.class))).thenReturn(domain);
        when(atualizar.atualizar(domain)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(response);

        mvc.perform(put("/api/v1/restaurantes/3")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(req))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.donoId").value(3));
    }
}
