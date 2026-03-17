package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Restaurante domain tests")
class RestauranteTest {

    @Test
    void criarSucesso() {
        var r = Restaurante.criar("R", "End", "Tipo", "10:00-22:00", 1L);
        assertNull(r.getId());
        assertEquals("R", r.getNome());
        assertEquals("End", r.getEndereco());
        assertEquals("Tipo", r.getTipoCozinha());
        assertEquals("10:00-22:00", r.getHorarioFuncionamento());
        assertEquals(1L, r.getDonoId());
        assertNotNull(r.getDataUltimaAlteracao());
    }

    @Test
    void validarCamposObrigatorios() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar(null, "e", "t", "h", 1L));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar("n", "", "t", "h", 1L));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar("n", "e", null, "h", 1L));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar("n", "e", "t", "  ", 1L));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar("n", "e", "t", "h", null));
    }

    @Test
    void atualizarSucesso() {
        var r = Restaurante.existente(3L, "Old", "E", "T", "H", 2L);
        r.atualizar("New", "NE", "NT", "NH");
        assertEquals("New", r.getNome());
        assertEquals("NE", r.getEndereco());
        assertEquals("NT", r.getTipoCozinha());
        assertEquals("NH", r.getHorarioFuncionamento());
        assertNotNull(r.getDataUltimaAlteracao());
    }

    @Test
    void equalsHashCode() {
        var a = Restaurante.existente(1L, "n", "e", "t", "h", 1L);
        var b = Restaurante.existente(1L, "x", "y", "z", "w", 2L);
        var c = Restaurante.existente(2L, "n", "e", "t", "h", 1L);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }
}
