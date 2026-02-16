package br.com.gestrest.api.application.usecase.impl.restaurante;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.in.restaurante.ListarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarRestauranteUseCaseImpl implements ListarRestauranteUseCase {

    private final RestauranteRepositoryPort repository;

    @Override
    public List<Restaurante> executar() {
        return repository.listar();
    }
}

