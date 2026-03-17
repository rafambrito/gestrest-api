package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ItemCardapio domain tests")
class ItemCardapioTest {

    @Test
    @DisplayName("Criar item com sucesso")
    void criarSucesso() {
        var item = ItemCardapio.criar("Nome", "Desc", new BigDecimal("10.00"), 1L);
        assertNull(item.getId());
        assertEquals("Nome", item.getNome());
        assertEquals(0, item.getPreco().compareTo(new BigDecimal("10.00")));
        assertEquals(1L, item.getRestauranteId());
        assertNotNull(item.getDataUltimaAlteracao());
    }

    @Test
    @DisplayName("Falha quando nome nulo ou em branco")
    void nomeInvalido() {
        assertThrows(IllegalArgumentException.class, () -> ItemCardapio.criar(null, "d", new BigDecimal("1"), 1L));
        assertThrows(IllegalArgumentException.class, () -> ItemCardapio.criar("   ", "d", new BigDecimal("1"), 1L));
    }

    @Test
    @DisplayName("Falha quando preco nulo ou menor/igual zero")
    void precoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> ItemCardapio.criar("n", "d", null, 1L));
        assertThrows(IllegalArgumentException.class, () -> ItemCardapio.criar("n", "d", new BigDecimal("0.00"), 1L));
        assertThrows(IllegalArgumentException.class, () -> ItemCardapio.criar("n", "d", new BigDecimal("-1"), 1L));
    }

    @Test
    @DisplayName("Falha quando restauranteId nulo")
    void restauranteIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> ItemCardapio.criar("n", "d", new BigDecimal("1"), null));
    }

    @Test
    @DisplayName("Atualizar item atualiza campos e data")
    void atualizarSucesso() {
        var item = ItemCardapio.existente(5L, "Old", "OldDesc", new BigDecimal("5.00"), 2L);
        item.atualizar("New", "NewDesc", new BigDecimal("7.50"));
        assertEquals("New", item.getNome());
        assertEquals("NewDesc", item.getDescricao());
        assertEquals(0, item.getPreco().compareTo(new BigDecimal("7.50")));
        assertNotNull(item.getDataUltimaAlteracao());
    }

    @Test
    @DisplayName("Equals e hashCode baseados em id")
    void equalsHashCode() {
        var a = ItemCardapio.existente(1L, "n", "d", new BigDecimal("1"), 1L);
        var b = ItemCardapio.existente(1L, "x", "y", new BigDecimal("2"), 2L);
        var c = ItemCardapio.existente(2L, "n", "d", new BigDecimal("1"), 1L);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
