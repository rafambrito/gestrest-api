package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ItemCardapioDomainTest {

    @Test
    void criarValido() {
        var i = ItemCardapio.criar("José Pereira", "Massa fresca recheada com ricota e espinafre", BigDecimal.TEN, 2L,
                false, "/itens/jose-pereira.jpg");
        assertEquals("José Pereira", i.getNome());
    }

    @Test
    void criarInvalidoPreco() {
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar("José Pereira", "Massa fresca recheada com ricota e espinafre", BigDecimal.ZERO, 2L, false,
                "/itens/jose-pereira.jpg"));
    }

    @Test
    void atualizarValido() {
        var i = ItemCardapio.existente(1L, "José Pereira", "Massa fresca recheada com ricota e espinafre", BigDecimal.TEN, 2L,
            true, "/itens/jose-pereira.jpg");
        i.atualizar("Rafael Brito", "Massa fresca recheada com ricota, espinafre e nozes", BigDecimal.valueOf(45), false,
            "/itens/rafael-brito.jpg");
        assertEquals("Rafael Brito", i.getNome());
    }
}
