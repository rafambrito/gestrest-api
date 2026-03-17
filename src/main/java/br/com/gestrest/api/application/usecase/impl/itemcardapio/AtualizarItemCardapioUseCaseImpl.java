package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.AtualizarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import br.com.gestrest.api.domain.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizarItemCardapioUseCaseImpl implements AtualizarItemCardapioUseCase {

	private final ItemCardapioRepositoryPort repository;

	@Override
	public ItemCardapio atualizar(ItemCardapio item) {

		var existente = repository.buscarPorId(item.getId())
				.orElseThrow(() -> new EntityNotFoundException(item.getId(), "Item"));

		existente.atualizar(item.getNome(), item.getDescricao(), item.getPreco());

		return repository.salvar(existente);
	}
}