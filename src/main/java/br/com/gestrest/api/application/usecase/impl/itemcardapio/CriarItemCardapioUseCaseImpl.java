package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.CriarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriarItemCardapioUseCaseImpl implements CriarItemCardapioUseCase {

	private final ItemCardapioRepositoryPort repository;

	@Override
	public ItemCardapio criar(ItemCardapio item) {
		return repository.salvar(item);
	}
}
