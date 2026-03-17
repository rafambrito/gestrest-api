package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ItemCardapioDomainTest {

    @Test
    void criarValido() {
        var i = ItemCardapio.criar("Nome", "Desc", BigDecimal.TEN, 2L);
        assertEquals("Nome", i.getNome());
    }

    @Test
    void criarInvalidoPreco() {
        assertThrows(IllegalArgumentException.class, () -> ItemCardapio.criar("N", "D", BigDecimal.ZERO, 2L));
    }

    @Test
    void atualizarValido() {
        var i = ItemCardapio.existente(1L, "N", "D", BigDecimal.TEN, 2L);
        i.atualizar("NN", "DD", BigDecimal.valueOf(5));
        assertEquals("NN", i.getNome());
    }
}
