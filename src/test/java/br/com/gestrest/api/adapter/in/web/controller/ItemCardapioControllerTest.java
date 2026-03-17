package br.com.gestrest.api.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.ItemCardapioResponse;
import br.com.gestrest.api.adapter.in.web.mapper.ItemCardapioWebMapper;
import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.AtualizarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.BuscarItemCardapioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.CriarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.ExcluirItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.ListarItensPorRestauranteUseCase;

import org.springframework.boot.test.mock.mockito.SpyBean;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ItemCardapioController.class)
class ItemCardapioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarItemCardapioUseCase criar;

    @MockBean
    private AtualizarItemCardapioUseCase atualizar;

    @MockBean
    private BuscarItemCardapioPorIdUseCase buscar;

    @MockBean
    private ListarItensPorRestauranteUseCase listar;

    @MockBean
    private ExcluirItemCardapioUseCase excluir;

    @MockBean
    private ItemCardapioWebMapper mapper;

    @Test
    void criar_sucesso() throws Exception {
        var req = new CriarItemCardapioRequest("Nome", "Desc", new BigDecimal("10.00"), 3L);
        var domain = ItemCardapio.criar("Nome", "Desc", new BigDecimal("10.00"), 3L);
        var criado = ItemCardapio.existente(5L, "Nome", "Desc", new BigDecimal("10.00"), 3L);
        var response = new ItemCardapioResponse(5L, "Nome", "Desc", new BigDecimal("10.00"), 3L);

        when(mapper.toDomain(any(CriarItemCardapioRequest.class))).thenReturn(domain);
        when(criar.criar(any())).thenReturn(criado);
        when(mapper.toResponse(criado)).thenReturn(response);

        mvc.perform(post("/api/v1/itens-cardapio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/itens-cardapio/5"))
                .andExpect(jsonPath("$.nome").value("Nome"))
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void buscar_sucesso() throws Exception {
        var domain = ItemCardapio.existente(2L, "N", "D", new BigDecimal("5.00"), 1L);
        var response = new ItemCardapioResponse(2L, "N", "D", new BigDecimal("5.00"), 1L);

        when(buscar.buscarPorId(2L)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(response);

        mvc.perform(get("/api/v1/itens-cardapio/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nome").value("N"));
    }

    @Test
    void buscar_nao_encontrado() throws Exception {
        when(buscar.buscarPorId(eq(99L))).thenThrow(new RestauranteNaoEncontradoException(99L));

        mvc.perform(get("/api/v1/itens-cardapio/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
