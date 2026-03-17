package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Usuario domain tests")
class UsuarioTest {

    @Test
    void criarEFlags() {
        var tipoDono = TipoUsuario.existente(1L, "DONO_RESTAURANTE");
        var tipoCliente = TipoUsuario.existente(2L, "CLIENTE");

        var dono = Usuario.criar("D", "d@d.com", "d", "s", "e", tipoDono);
        var cliente = Usuario.criar("C", "c@c.com", "c", "s", "e", tipoCliente);

        assertTrue(dono.isDono());
        assertFalse(dono.isCliente());

        assertTrue(cliente.isCliente());
        assertFalse(cliente.isDono());
    }

    @Test
    void alterarTipoEAtualizarDados() {
        var tipo = TipoUsuario.existente(3L, "OLD");
        var u = Usuario.criar("N", "e@e.com", "l", "s", "end", tipo);
        var novo = TipoUsuario.existente(4L, "DONO_RESTAURANTE");
        u.alterarTipoUsuario(novo);
        assertEquals("DONO_RESTAURANTE", u.getTipoUsuario().getNome());

        u.atualizarDados("NovoNome");
        assertEquals("NovoNome", u.getNome());
    }
}
