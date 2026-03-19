package br.com.gestrest.api.domain.model;

import java.util.Arrays;
import java.util.Optional;

public enum TipoUsuarioEnum {

    DONO_RESTAURANTE(1L, "Dono de Restaurante"),
    CLIENTE(2L, "Cliente");

    private final Long id;
    private final String descricao;

    TipoUsuarioEnum(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Optional<TipoUsuarioEnum> fromId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(tipo -> tipo.id.equals(id))
                .findFirst();
    }
}
