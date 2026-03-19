package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.CriarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriarItemCardapioUseCaseImpl implements CriarItemCardapioUseCase {

    private final ItemCardapioRepositoryPort repository;
    private final RestauranteRepositoryPort restauranteRepository;

    @Override
    public ItemCardapio criar(ItemCardapio item) {
        var restauranteId = item.getRestauranteId();
        restauranteRepository.buscarPorId(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));

        return repository.salvar(item);
    }
}