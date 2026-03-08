package br.com.gestrest.api.application.usecase.impl.restaurante;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.exception.RecursoEmUsoException;
import br.com.gestrest.api.domain.model.ports.in.restaurante.ExcluirRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcluirRestauranteUseCaseImpl implements ExcluirRestauranteUseCase {

    private final RestauranteRepositoryPort repository;
    private final ItemCardapioRepositoryPort itemRepository;

    @Override
    public void deletar(Long id) {
        var itens = itemRepository.listarPorRestauranteId(id);
        if (itens != null && !itens.isEmpty()) {
            throw new RecursoEmUsoException("O restaurante possui itens de cardápio e não pode ser excluído");
        }

        repository.deletar(id);
    }
}