package br.com.gestrest.api.domain.model.ports.in.itemcardapio;

import br.com.gestrest.api.domain.model.ItemCardapio;

public interface CriarItemCardapioUseCase {
	ItemCardapio criar(ItemCardapio item);
}
