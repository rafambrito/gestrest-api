package br.com.gestrest.api.adapter.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.gestrest.api.adapter.out.persistence.entity.RestauranteEntity;
import br.com.gestrest.api.domain.model.Restaurante;

class RestaurantePersistenceMapperTest {

    private final RestaurantePersistenceMapper mapper = new RestaurantePersistenceMapper();

    @Test
    void toEntityAndBack() {
        var domain = Restaurante.existente(3L, "N", "E", "T", "H", 4L);
        RestauranteEntity entity = mapper.toEntity(domain);

        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getNome(), entity.getNome());

        var back = mapper.toDomain(entity);
        assertEquals(entity.getNome(), back.getNome());
        assertEquals(entity.getDonoId(), back.getDonoId());
    }
}
