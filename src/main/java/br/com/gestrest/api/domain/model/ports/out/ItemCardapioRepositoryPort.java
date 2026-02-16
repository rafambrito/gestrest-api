package br.com.gestrest.api.domain.model.ports.out;

import java.util.List;
import java.util.Optional;

import br.com.gestrest.api.domain.model.ItemCardapio;

public interface ItemCardapioRepositoryPort {
    ItemCardapio salvar(ItemCardapio item);
    
    Optional<ItemCardapio> buscarPorId(Long id);
    
    List<ItemCardapio> listarPorRestauranteId(Long restauranteId);
    
    void deletar(Long id);
}
