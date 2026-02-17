package br.com.gestrest.api.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.out.persistence.entity.RestauranteEntity;
import br.com.gestrest.api.domain.model.Restaurante;

@Component
public class RestaurantePersistenceMapper {

    public RestauranteEntity toEntity(Restaurante domain) {
        return new RestauranteEntity(
                domain.getId(),
                domain.getNome(),
                domain.getEndereco(),
                domain.getTipoCozinha(),
                domain.getHorarioFuncionamento(),
                domain.getDonoId(),
                domain.getDataUltimaAlteracao()
        );
    }

    public Restaurante toDomain(RestauranteEntity entity) {
        return Restaurante.existente(
                entity.getId(),
                entity.getNome(),
                entity.getEndereco(),
                entity.getTipoCozinha(),
                entity.getHorarioFuncionamento(),
                entity.getDonoId()
        );
    }
}
