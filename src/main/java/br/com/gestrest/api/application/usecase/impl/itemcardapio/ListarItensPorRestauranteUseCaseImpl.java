package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.ListarItensPorRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListarItensPorRestauranteUseCaseImpl implements ListarItensPorRestauranteUseCase {

    private final ItemCardapioRepositoryPort repository;

    @Override
    public List<ItemCardapio> listarPorRestauranteId(Long restauranteId) {
        return repository.listarPorRestauranteId(restauranteId);
    }
}
