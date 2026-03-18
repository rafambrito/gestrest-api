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
        var domain = Restaurante.existente(1L, "João da Silva", "Rua das Rosas, São Paulo/SP", "Contemporanea", "Seg-Sab 11:30-23:00", 2L);
        var entity = new RestauranteEntity();
        when(mapper.toEntity(any(Restaurante.class))).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.salvar(domain);
        assertEquals(domain.getNome(), res.getNome());
    }

    @Test
    void buscarPorId() {
        var domain = Restaurante.existente(2L, "José Pereira", "Rua das Rosas, São Paulo/SP", "Italiana", "Ter-Dom 12:00-23:00", 3L);
        var entity = new RestauranteEntity();
        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.buscarPorId(2L);
        assertEquals("José Pereira", res.get().getNome());
    }

    @Test
    void listar() {
        var domain = Restaurante.existente(3L, "Rafael Brito", "Avenida Beija Flor, São Paulo/SP", "Brasileira", "Seg-Dom 11:00-22:30", 4L);
        var entity = new RestauranteEntity();
        when(repository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var res = adapter.listar();
        assertEquals(1, res.size());
        assertEquals("Rafael Brito", res.get(0).getNome());
    }
}
