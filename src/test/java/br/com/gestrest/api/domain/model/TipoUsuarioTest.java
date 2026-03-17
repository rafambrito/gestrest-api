package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TipoUsuario domain tests")
class TipoUsuarioTest {

    @Test
    void criarSucesso() {
        var t = TipoUsuario.criar("CLIENTE");
        assertNull(t.getId());
        assertEquals("CLIENTE", t.getNome());
    }

    @Test
    void existenteIdNuloDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> TipoUsuario.existente(null, "n"));
    }

    @Test
    void atualizarDadosEIgualdade() {
        var t = TipoUsuario.existente(2L, "OLD");
        t.atualizarDados("NEW");
        assertEquals("NEW", t.getNome());

        var a = TipoUsuario.existente(1L, "X");
        var b = TipoUsuario.existente(1L, "Y");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
