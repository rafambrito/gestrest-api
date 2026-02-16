package br.com.gestrest.api.application.usecase.impl.restaurante;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.in.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscarRestaurantePorIdUseCaseImpl implements BuscarRestaurantePorIdUseCase {

    private final RestauranteRepositoryPort repository;

    @Override
    public Restaurante executar(Long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
    }
}
