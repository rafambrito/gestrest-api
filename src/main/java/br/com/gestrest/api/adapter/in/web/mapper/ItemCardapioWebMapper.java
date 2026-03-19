package br.com.gestrest.api.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.ItemCardapioResponse;
import br.com.gestrest.api.domain.model.ItemCardapio;

@Component
public class ItemCardapioWebMapper {

    public ItemCardapio toDomain(CriarItemCardapioRequest request) {
        return ItemCardapio.criar(
                request.nome(),
                request.descricao(),
                request.preco(),
            request.restauranteId(),
                request.disponivelSomenteNoLocal(),
            request.fotoPath()
        );
    }

    public ItemCardapio toDomain(Long id, AtualizarItemCardapioRequest request) {
        return ItemCardapio.existente(
                id,
                request.nome(),
                request.descricao(),
                request.preco(),
            request.restauranteId(),
            request.disponivelSomenteNoLocal(),
            request.fotoPath()
        );
    }

    public ItemCardapioResponse toResponse(ItemCardapio domain) {
        return new ItemCardapioResponse(
                domain.getId(),
                domain.getNome(),
                domain.getDescricao(),
                domain.getPreco(),
                domain.isDisponivelSomenteNoLocal(),
                domain.getFotoPath(),
                domain.getRestauranteId()
        );
    }
}

