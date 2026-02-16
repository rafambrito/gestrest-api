package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.ports.in.itemcardapio.ExcluirItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcluirItemCardapioUseCaseImpl implements ExcluirItemCardapioUseCase {

    private final ItemCardapioRepositoryPort repository;

    @Override
    public void deletar(Long id) {
        repository.deletar(id);
    }
}

