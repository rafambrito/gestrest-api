package br.com.gestrest.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.out.persistence.mapper.RestaurantePersistenceMapper;
import br.com.gestrest.api.adapter.out.persistence.repository.RestauranteJpaRepository;
import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestauranteRepositoryAdapter implements RestauranteRepositoryPort {

    private final RestauranteJpaRepository repository;
    private final RestaurantePersistenceMapper mapper;

    @Override
    public Restaurante salvar(Restaurante restaurante) {
        var entity = mapper.toEntity(restaurante);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Restaurante> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Restaurante> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
