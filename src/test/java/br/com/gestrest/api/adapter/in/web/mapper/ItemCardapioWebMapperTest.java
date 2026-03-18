package br.com.gestrest.api.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.ItemCardapioResponse;
import br.com.gestrest.api.domain.model.ItemCardapio;

class ItemCardapioWebMapperTest {

    private final ItemCardapioWebMapper mapper = new ItemCardapioWebMapper();

    @Test
    void toDomainFromCreate() {
        var req = new CriarItemCardapioRequest("Camarão na Moranga", "Camarao refogado servido na moranga", BigDecimal.TEN,
                true, "/imagens/itens/camarao-moranga.jpg", 4L);
        var domain = mapper.toDomain(req);
        assertEquals("Camarão na Moranga", domain.getNome());
        assertEquals(4L, domain.getRestauranteId().longValue());
        assertEquals(true, domain.isDisponivelSomenteNoLocal());
    }

    @Test
    void toDomainFromUpdate() {
        var req = new AtualizarItemCardapioRequest("NU", "DU", BigDecimal.valueOf(5), false, "/itens/nu.jpg", 8L);
        var domain = mapper.toDomain(3L, req);

        assertEquals(3L, domain.getId());
        assertEquals("NU", domain.getNome());
        assertEquals(8L, domain.getRestauranteId());
    }

    @Test
    void toResponse() {
        var domain = ItemCardapio.existente(7L, "Bruschetta de Tomate", "Pao italiano, tomate confit e manjericao", BigDecimal.valueOf(23.5),
                6L, false, "/itens/bruschetta.jpg");
        ItemCardapioResponse resp = mapper.toResponse(domain);

        assertEquals(7L, resp.id().longValue());
        assertEquals("Bruschetta de Tomate", resp.nome());
        assertEquals("/itens/bruschetta.jpg", resp.fotoPath());
    }
}
