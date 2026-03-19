package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TipoUsuario testes de dominio")
class TipoUsuarioTest {

    @Test
    void criarSucesso() {
        var t = TipoUsuario.criar("CLIENTE");
        assertNull(t.getId());
        assertEquals("CLIENTE", t.getNome());
    }

    @Test
    void existenteIdNuloDeveFalhar() {
        assertThrows(IllegalArgumentException.class, () -> TipoUsuario.existente(null, "GERENTE_RESTAURANTE"));
    }

    @Test
    void atualizarDadosEIgualdade() {
        var t = TipoUsuario.existente(2L, "ATENDENTE");
        t.atualizarDados("COORDENADOR_ATENDIMENTO");
        assertEquals("COORDENADOR_ATENDIMENTO", t.getNome());

        var a = TipoUsuario.existente(1L, "CLIENTE");
        var b = TipoUsuario.existente(1L, "DONO_RESTAURANTE");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
