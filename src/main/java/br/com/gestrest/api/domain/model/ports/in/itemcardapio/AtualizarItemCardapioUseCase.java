package br.com.gestrest.api.domain.model.ports.in.itemcardapio;

import br.com.gestrest.api.domain.model.ItemCardapio;

public interface AtualizarItemCardapioUseCase {
    ItemCardapio atualizar(ItemCardapio item);
}
