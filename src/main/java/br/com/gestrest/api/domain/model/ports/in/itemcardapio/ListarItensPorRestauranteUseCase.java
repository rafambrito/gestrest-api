package br.com.gestrest.api.domain.model.ports.in.itemcardapio;

import java.util.List;

import br.com.gestrest.api.domain.model.ItemCardapio;

public interface ListarItensPorRestauranteUseCase {
	List<ItemCardapio> listarPorRestauranteId(Long restauranteId);
}
