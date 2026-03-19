package br.com.gestrest.api.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.out.persistence.entity.ItemCardapioEntity;
import br.com.gestrest.api.domain.model.ItemCardapio;

@Component
public class ItemCardapioPersistenceMapper {

    public ItemCardapioEntity toEntity(ItemCardapio domain) {
        return new ItemCardapioEntity(
                domain.getId(),
                domain.getNome(),
                domain.getDescricao(),
                domain.getPreco(),
                domain.getRestauranteId(),
                domain.isDisponivelSomenteNoLocal(),
                domain.getFotoPath(),
                domain.getDataUltimaAlteracao()
        );
    }

    public ItemCardapio toDomain(ItemCardapioEntity entity) {
        return ItemCardapio.existente(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getRestauranteId(),
                Boolean.TRUE.equals(entity.getDisponivelSomenteNoLocal()),
                entity.getFotoPath()
        );
    }
}