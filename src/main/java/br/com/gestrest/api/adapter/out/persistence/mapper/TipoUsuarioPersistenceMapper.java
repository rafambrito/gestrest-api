package br.com.gestrest.api.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.out.persistence.entity.TipoUsuarioEntity;
import br.com.gestrest.api.domain.model.TipoUsuario;

@Component
public class TipoUsuarioPersistenceMapper {

    public TipoUsuarioEntity toEntity(TipoUsuario domain) {

        if (domain == null) {
            return null;
        }

        TipoUsuarioEntity entity = new TipoUsuarioEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());

        return entity;
    }

    public TipoUsuario toDomain(TipoUsuarioEntity entity) {

        if (entity == null) {
            return null;
        }

        return TipoUsuario.existente(
                entity.getId(),
                entity.getNome()
        );
    }
}