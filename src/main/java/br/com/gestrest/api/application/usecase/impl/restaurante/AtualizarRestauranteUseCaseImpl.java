package br.com.gestrest.api.application.usecase.impl.restaurante;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.in.restaurante.AtualizarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizarRestauranteUseCaseImpl implements AtualizarRestauranteUseCase {

    private final RestauranteRepositoryPort repository;

    @Override
    public Restaurante atualizar(Restaurante restaurante) {

        var existente = repository.buscarPorId(restaurante.getId())
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restaurante.getId()));

        existente.atualizar(
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento()
        );

        return repository.salvar(existente);
    }
}