package br.com.gestrest.api.adapter.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import br.com.gestrest.api.adapter.in.web.mapper.ItemCardapioPersistenceMapper;
import br.com.gestrest.api.adapter.out.persistence.entity.ItemCardapioEntity;
import br.com.gestrest.api.domain.model.ItemCardapio;

class ItemCardapioPersistenceMapperTest {

    private final ItemCardapioPersistenceMapper mapper = new ItemCardapioPersistenceMapper();

    @Test
    void toEntityAndToDomain() {
        var domain = ItemCardapio.existente(2L, "Nome", "Desc", BigDecimal.valueOf(12.5), 5L);
        var entity = mapper.toEntity(domain);

        assertEquals(domain.getNome(), entity.getNome());
        assertEquals(domain.getRestauranteId(), entity.getRestauranteId());

        var e = new ItemCardapioEntity(3L, "N", "D", BigDecimal.valueOf(7.5), 8L, LocalDateTime.now());
        var back = mapper.toDomain(e);

        assertEquals(e.getId(), back.getId());
        assertEquals(e.getNome(), back.getNome());
    }
}