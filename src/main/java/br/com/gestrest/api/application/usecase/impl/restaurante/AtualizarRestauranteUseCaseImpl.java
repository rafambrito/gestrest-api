package br.com.gestrest.api.application.usecase.impl.restaurante;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.in.restaurante.AtualizarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizarRestauranteUseCaseImpl implements AtualizarRestauranteUseCase {

    private final RestauranteRepositoryPort repository;

    @Override
    public Restaurante atualizar(Restaurante restaurante) {

        var existente = repository.buscarPorId(restaurante.getId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        existente.atualizar(
                restaurante.getNome(),
                restaurante.getEndereco()
        );

        return repository.salvar(existente);
    }
}
