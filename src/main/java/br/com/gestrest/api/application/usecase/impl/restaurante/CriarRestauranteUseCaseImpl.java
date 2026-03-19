package br.com.gestrest.api.application.usecase.impl.restaurante;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.in.restaurante.CriarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriarRestauranteUseCaseImpl implements CriarRestauranteUseCase {

    private final RestauranteRepositoryPort repository;
    private final ValidarDonoRestauranteService validarDonoRestauranteService;

    @Override
    public Restaurante criar(Restaurante restaurante) {
        validarDonoRestauranteService.validar(restaurante.getDonoId());

        return repository.salvar(restaurante);
    }
}