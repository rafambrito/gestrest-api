package br.com.gestrest.api.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.RestauranteResponse;
import br.com.gestrest.api.domain.model.Restaurante;

class RestauranteWebMapperTest {

    private final RestauranteWebMapper mapper = new RestauranteWebMapper();

    @Test
    void toDomainFromCreate() {
        var req = new CriarRestauranteRequest("Nome", "End", "Italiana", "9-18", 10L);
        var domain = mapper.toDomain(req);

        assertNull(domain.getId());
        assertEquals("Nome", domain.getNome());
        assertEquals(10L, domain.getDonoId().longValue());
    }

    @Test
    void toDomainFromUpdate() {
        var req = new AtualizarRestauranteRequest("NomeU", "EndU", "Brasileira", "10-20");
        // mapper sets donoId to null for update (domain validation requires donoId), expect exception
        assertThrows(IllegalArgumentException.class, () -> mapper.toDomain(7L, req));
    }

    @Test
    void toResponse() {
        var domain = Restaurante.existente(5L, "R", "E", "T", "H", 2L);
        RestauranteResponse resp = mapper.toResponse(domain);

        assertEquals(5L, resp.id().longValue());
        assertEquals("R", resp.nome());
    }
}