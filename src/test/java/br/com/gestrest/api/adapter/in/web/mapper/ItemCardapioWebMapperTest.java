package br.com.gestrest.api.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        var req = new CriarItemCardapioRequest("N", "D", BigDecimal.TEN, 4L);
        var domain = mapper.toDomain(req);
        assertEquals("N", domain.getNome());
        assertEquals(4L, domain.getRestauranteId().longValue());
    }

    @Test
    void toDomainFromUpdate_shouldThrow() {
        var req = new AtualizarItemCardapioRequest("NU", "DU", BigDecimal.valueOf(5));
        // mapper sets restauranteId to null which domain validation forbids: expect exception
        assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(3L, req));
    }

    @Test
    void toResponse() {
        var domain = ItemCardapio.existente(7L, "R", "E", BigDecimal.valueOf(3.5), 6L);
        ItemCardapioResponse resp = mapper.toResponse(domain);

        assertEquals(7L, resp.id().longValue());
        assertEquals("R", resp.nome());
    }
}
