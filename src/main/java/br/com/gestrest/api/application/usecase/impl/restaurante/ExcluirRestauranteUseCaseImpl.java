package br.com.gestrest.api.application.usecase.impl.restaurante;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.ports.in.restaurante.ExcluirRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcluirRestauranteUseCaseImpl implements ExcluirRestauranteUseCase {

    private final RestauranteRepositoryPort repository;

    @Override
    public void deletar(Long id) {
        repository.deletar(id);
    }
}
