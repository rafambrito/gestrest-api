package br.com.gestrest.api.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.out.persistence.entity.UsuarioEntity;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;

@Component
public class UsuarioPersistenceMapper {

    public Usuario toDomain(UsuarioEntity entity) {

        return Usuario.existente(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getSenha(),
                entity.getEndereco(),
                TipoUsuario.existente(
                        entity.getTipoUsuario().getId(),
                        entity.getTipoUsuario().getNome()
                )
        );
    }
}