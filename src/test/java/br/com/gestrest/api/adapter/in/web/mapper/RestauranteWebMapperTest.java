package br.com.gestrest.api.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.RestauranteResponse;
import br.com.gestrest.api.domain.model.Restaurante;

class RestauranteWebMapperTest {

    private final RestauranteWebMapper mapper = new RestauranteWebMapper();

    @Test
    void toDomainFromCreate() {
        var req = new CriarRestauranteRequest("João da Silva", "Rua das Rosas, São Paulo/SP", "Italiana", "9-18", 10L);
        var domain = mapper.toDomain(req);

        assertNull(domain.getId());
        assertEquals("João da Silva", domain.getNome());
        assertEquals(10L, domain.getDonoId().longValue());
    }

    @Test
    void toDomainFromUpdate() {
        var req = new AtualizarRestauranteRequest("Rafael Brito", "Rua das Rosas, São Paulo/SP", "Brasileira", "10-20", 7L);
        var domain = mapper.toDomain(7L, req);

        assertEquals(7L, domain.getId());
        assertEquals("Rafael Brito", domain.getNome());
        assertEquals(7L, domain.getDonoId());
    }

    @Test
    void toResponse() {
        var domain = Restaurante.existente(5L, "João da Silva", "Rua das Rosas, São Paulo/SP", "Churrascaria", "11:00-23:00", 2L);
        RestauranteResponse resp = mapper.toResponse(domain);

        assertEquals(5L, resp.id().longValue());
        assertEquals("João da Silva", resp.nome());
    }
}