package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TipoUsuarioEnum tests")
class TipoUsuarioEnumTest {

    @Test
    @DisplayName("Deve possuir os atributos corretos para DONO_RESTAURANTE")
    void donoRestauranteDeveTermAtributosCorretos() {
        var tipo = TipoUsuarioEnum.DONO_RESTAURANTE;
        assertEquals(1L, tipo.getId());
        assertEquals("Dono de Restaurante", tipo.getDescricao());
    }

    @Test
    @DisplayName("Deve possuir os atributos corretos para CLIENTE")
    void clienteDeveTermAtributosCorretos() {
        var tipo = TipoUsuarioEnum.CLIENTE;
        assertEquals(2L, tipo.getId());
        assertEquals("Cliente", tipo.getDescricao().trim());
    }

    @Test
    @DisplayName("fromId deve retornar DONO_RESTAURANTE para id 1")
    void fromIdDeveRetornarDonoRestaurante() {
        var resultado = TipoUsuarioEnum.fromId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(TipoUsuarioEnum.DONO_RESTAURANTE, resultado.get());
    }

    @Test
    @DisplayName("fromId deve retornar CLIENTE para id 2")
    void fromIdDeveRetornarCliente() {
        var resultado = TipoUsuarioEnum.fromId(2L);
        assertTrue(resultado.isPresent());
        assertEquals(TipoUsuarioEnum.CLIENTE, resultado.get());
    }

    @Test
    @DisplayName("fromId deve retornar Optional vazio para id desconhecido")
    void fromIdDeveRetornarVazioParaIdDesconhecido() {
        var resultado = TipoUsuarioEnum.fromId(99L);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("fromId deve retornar Optional vazio para id nulo")
    void fromIdDeveRetornarVazioParaIdNulo() {
        var resultado = TipoUsuarioEnum.fromId(null);
        assertTrue(resultado.isEmpty());
    }
}
