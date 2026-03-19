package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ItemCardapio testes de dominio")
class ItemCardapioTest {

    @Test
    @DisplayName("Criar item com sucesso")
    void criarSucesso() {
        var item = ItemCardapio.criar("João da Silva", "Acompanha pure de batata e legumes no vapor", new BigDecimal("49.90"), 1L,
            false, "/itens/joao-da-silva.jpg");
        assertNull(item.getId());
        assertEquals("João da Silva", item.getNome());
        assertEquals(0, item.getPreco().compareTo(new BigDecimal("49.90")));
        assertEquals(1L, item.getRestauranteId());
        assertNotNull(item.getDataUltimaAlteracao());
    }

    @Test
    @DisplayName("Falha quando nome nulo ou em branco")
    void nomeInvalido() {
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar(null, "Prato com ingredientes frescos", new BigDecimal("29.90"), 1L, false, "/itens/teste.jpg"));
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar("   ", "Prato com ingredientes frescos", new BigDecimal("29.90"), 1L, false, "/itens/teste.jpg"));
    }

    @Test
    @DisplayName("Falha quando preco nulo ou menor/igual zero")
    void precoInvalido() {
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar("Penne ao Pomodoro", "Massa artesanal com molho de tomate fresco", null, 1L, false, "/itens/teste.jpg"));
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar("Penne ao Pomodoro", "Massa artesanal com molho de tomate fresco", new BigDecimal("0.00"), 1L, false,
                "/itens/teste.jpg"));
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar("Penne ao Pomodoro", "Massa artesanal com molho de tomate fresco", new BigDecimal("-1"), 1L, false,
                "/itens/teste.jpg"));
    }

    @Test
    @DisplayName("Falha quando restauranteId nulo")
    void restauranteIdNulo() {
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar("Penne ao Pomodoro", "Massa artesanal com molho de tomate fresco", new BigDecimal("29.90"), null, false,
                "/itens/teste.jpg"));
        }

        @Test
        @DisplayName("Falha quando caminho da foto é nulo ou em branco")
        void fotoPathInvalido() {
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar("Penne ao Pomodoro", "Massa artesanal com molho de tomate fresco", new BigDecimal("29.90"), 1L, true, null));
        assertThrows(IllegalArgumentException.class,
            () -> ItemCardapio.criar("Penne ao Pomodoro", "Massa artesanal com molho de tomate fresco", new BigDecimal("29.90"), 1L, true, " "));
    }

    @Test
    @DisplayName("Atualizar item atualiza campos e data")
    void atualizarSucesso() {
        var item = ItemCardapio.existente(5L, "José Pereira", "Pao brioche, queijo prato e maionese da casa", new BigDecimal("28.00"), 2L,
            true, "/itens/jose-pereira.jpg");
        item.atualizar("Rafael Brito", "Pao brioche, cheddar, bacon crocante e molho especial", new BigDecimal("34.50"), false,
            "/itens/rafael-brito.jpg");
        assertEquals("Rafael Brito", item.getNome());
        assertEquals("Pao brioche, cheddar, bacon crocante e molho especial", item.getDescricao());
        assertEquals(0, item.getPreco().compareTo(new BigDecimal("34.50")));
        assertEquals("/itens/rafael-brito.jpg", item.getFotoPath());
        assertNotNull(item.getDataUltimaAlteracao());
    }

    @Test
    @DisplayName("Equals e hashCode baseados em id")
    void equalsHashCode() {
        var a = ItemCardapio.existente(1L, "José Pereira", "Nhoque artesanal com molho de tomate", new BigDecimal("36.00"), 1L,
            false, "/itens/jose-pereira.jpg");
        var b = ItemCardapio.existente(1L, "Salmão ao Molho de Maracuja", "File de salmao com risoto de limao siciliano", new BigDecimal("69.00"),
            2L, true, "/itens/salmao.jpg");
        var c = ItemCardapio.existente(2L, "José Pereira", "Nhoque artesanal com molho de tomate", new BigDecimal("36.00"), 1L,
            false, "/itens/jose-pereira.jpg");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
