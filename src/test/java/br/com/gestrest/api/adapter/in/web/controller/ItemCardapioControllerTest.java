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

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.ItemCardapioResponse;
import br.com.gestrest.api.adapter.in.web.mapper.ItemCardapioWebMapper;
import br.com.gestrest.api.domain.exception.EntityNotFoundException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.AtualizarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.BuscarItemCardapioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.CriarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.ExcluirItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.ListarItensPorRestauranteUseCase;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ItemCardapioController.class)
class ItemCardapioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CriarItemCardapioUseCase criar;

    @MockitoBean
    private AtualizarItemCardapioUseCase atualizar;

    @MockitoBean
    private BuscarItemCardapioPorIdUseCase buscar;

    @MockitoBean
    private ListarItensPorRestauranteUseCase listar;

    @MockitoBean
    private ExcluirItemCardapioUseCase excluir;

    @MockitoBean
    private ItemCardapioWebMapper mapper;

    @Test
    void criar_sucesso() throws Exception {
        var req = new CriarItemCardapioRequest("Risoto de Cogumelos", "Arroz arboreo com mix de cogumelos frescos", new BigDecimal("42.90"),
            false, "/imagens/itens/risoto-cogumelos.jpg", 3L);
        var domain = ItemCardapio.criar("Risoto de Cogumelos", "Arroz arboreo com mix de cogumelos frescos", new BigDecimal("42.90"),
            3L, false, "/imagens/itens/risoto-cogumelos.jpg");
        var criado = ItemCardapio.existente(5L, "Risoto de Cogumelos", "Arroz arboreo com mix de cogumelos frescos", new BigDecimal("42.90"),
            3L, false, "/imagens/itens/risoto-cogumelos.jpg");
        var response = new ItemCardapioResponse(5L, "Risoto de Cogumelos", "Arroz arboreo com mix de cogumelos frescos", new BigDecimal("42.90"),
            false, "/imagens/itens/risoto-cogumelos.jpg", 3L);

        when(mapper.toDomain(any(CriarItemCardapioRequest.class))).thenReturn(domain);
        when(criar.criar(any())).thenReturn(criado);
        when(mapper.toResponse(criado)).thenReturn(response);

        mvc.perform(post("/api/v1/itens-cardapio")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(req))))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/itens-cardapio/5"))
            .andExpect(jsonPath("$.nome").value("Risoto de Cogumelos"))
                .andExpect(jsonPath("$.id").value(5));
    }

    @Test
    void buscar_sucesso() throws Exception {
        var domain = ItemCardapio.existente(2L, "File de Frango Grelhado", "Acompanha arroz integral e legumes", new BigDecimal("31.50"),
            1L, true, "/imagens/itens/file-frango.jpg");
        var response = new ItemCardapioResponse(2L, "File de Frango Grelhado", "Acompanha arroz integral e legumes", new BigDecimal("31.50"),
            true, "/imagens/itens/file-frango.jpg", 1L);

        when(buscar.buscarPorId(2L)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(response);

        mvc.perform(get("/api/v1/itens-cardapio/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.nome").value("File de Frango Grelhado"));
    }

    @Test
    void buscar_nao_encontrado() throws Exception {
        when(buscar.buscarPorId(eq(99L))).thenThrow(new EntityNotFoundException(99L, "Item"));

        mvc.perform(get("/api/v1/itens-cardapio/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizar_sem_restaurante_id_retorna_bad_request() throws Exception {
        String body = """
                {
                  \"nome\": \"Lasanha Premium\",
                  \"descricao\": \"Massa artesanal com molho rústico e queijo especial\",
                                    \"preco\": 65.00,
                                    \"disponivelSomenteNoLocal\": true,
                                    \"fotoPath\": \"/imagens/itens/lasanha-premium.jpg\"
                }
                """;

        mvc.perform(put("/api/v1/itens-cardapio/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors[0].field").value("restauranteId"));
    }

    @Test
    void atualizar_com_restaurante_id_retorna_sucesso() throws Exception {
        var req = new AtualizarItemCardapioRequest("Lasanha Premium", "Massa artesanal com molho rústico e queijo especial", new BigDecimal("65.00"),
            true, "/imagens/itens/lasanha-premium.jpg", 2L);
        var domain = ItemCardapio.existente(1L, "Lasanha Premium", "Massa artesanal com molho rústico e queijo especial", new BigDecimal("65.00"),
            2L, true, "/imagens/itens/lasanha-premium.jpg");
        var response = new ItemCardapioResponse(1L, "Lasanha Premium", "Massa artesanal com molho rústico e queijo especial", new BigDecimal("65.00"),
            true, "/imagens/itens/lasanha-premium.jpg", 2L);

        when(mapper.toDomain(eq(1L), any(AtualizarItemCardapioRequest.class))).thenReturn(domain);
        when(atualizar.atualizar(domain)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(response);

        mvc.perform(put("/api/v1/itens-cardapio/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(req))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.restauranteId").value(2));
    }
}
