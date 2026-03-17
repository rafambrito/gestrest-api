package br.com.gestrest.api.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.adapter.out.persistence.entity.RestauranteEntity;
import br.com.gestrest.api.adapter.out.persistence.mapper.RestaurantePersistenceMapper;
import br.com.gestrest.api.adapter.out.persistence.repository.RestauranteJpaRepository;
import br.com.gestrest.api.domain.model.Restaurante;

@ExtendWith(MockitoExtension.class)
class RestauranteRepositoryAdapterTest {

    @Mock
    private RestauranteJpaRepository repository;

    @Mock
    private RestaurantePersistenceMapper mapper;

    @InjectMocks
    private RestauranteRepositoryAdapter adapter;

    @Test
    void salvar() {
        var domain = Restaurante.existente(1L, "N", "E", "T", "H", 2L);
        var entity = new RestauranteEntity();
        when(mapper.toEntity(any(Restaurante.class))).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.salvar(domain);
        assertEquals(domain.getNome(), res.getNome());
    }

    @Test
    void buscarPorId() {
        var domain = Restaurante.existente(2L, "N2", "E2", "T2", "H2", 3L);
        var entity = new RestauranteEntity();
        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.buscarPorId(2L);
        assertEquals("N2", res.get().getNome());
    }

    @Test
    void listar() {
        var domain = Restaurante.existente(3L, "N3", "E3", "T3", "H3", 4L);
        var entity = new RestauranteEntity();
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.listar();
        assertEquals(1, res.size());
        assertEquals("N3", res.get(0).getNome());
    }
}
