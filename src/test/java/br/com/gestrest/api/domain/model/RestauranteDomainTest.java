package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RestauranteDomainTest {

    @Test
    void criarValido() {
        var r = Restaurante.criar("Nome", "End", "Tipo", "Horario", 10L);
        assertEquals("Nome", r.getNome());
    }

    @Test
    void criarInvalidoNome() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar(null, "E", "T", "H", 1L));
    }

    @Test
    void atualizarValido() {
        var r = Restaurante.existente(1L, "N", "E", "T", "H", 2L);
        r.atualizar("Novo", "NE", "NT", "NH");
        assertEquals("Novo", r.getNome());
    }
}
