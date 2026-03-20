package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Restaurante testes de dominio")
class RestauranteTest {

    @Test
    void criarSucesso() {
        var r = Restaurante.criar("João da Silva", "Rua das Rosas, São Paulo/SP", "Brasileira", "10:00-22:00", 1L);
        assertNull(r.getId());
        assertEquals("João da Silva", r.getNome());
        assertEquals("Rua das Rosas, São Paulo/SP", r.getEndereco());
        assertEquals("Brasileira", r.getTipoCozinha());
        assertEquals("10:00-22:00", r.getHorarioFuncionamento());
        assertEquals(1L, r.getDonoId());
        assertNotNull(r.getDataUltimaAlteracao());
    }

    @Test
    void validarCamposObrigatorios() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar(null, "Rua das Rosas, São Paulo/SP", "Brasileira", "10:00-22:00", 1L));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar("João da Silva", "", "Brasileira", "10:00-22:00", 1L));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar("João da Silva", "Rua das Rosas, São Paulo/SP", null, "10:00-22:00", 1L));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar("João da Silva", "Rua das Rosas, São Paulo/SP", "Brasileira", "  ", 1L));
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar("João da Silva", "Rua das Rosas, São Paulo/SP", "Brasileira", "10:00-22:00", null));
    }

    @Test
    void atualizarSucesso() {
        var r = Restaurante.existente(3L, "José Pereira", "Rua das Rosas, São Paulo/SP", "Italiana", "11:30-22:00", 2L);
        r.atualizar("Rafael Brito", "Rua das Rosas, São Paulo/SP", "Italiana Contemporanea", "11:30-23:00");
        assertEquals("Rafael Brito", r.getNome());
        assertEquals("Rua das Rosas, São Paulo/SP", r.getEndereco());
        assertEquals("Italiana Contemporanea", r.getTipoCozinha());
        assertEquals("11:30-23:00", r.getHorarioFuncionamento());
        assertNotNull(r.getDataUltimaAlteracao());
    }

    @Test
    void equalsHashCode() {
        var a = Restaurante.existente(1L, "João da Silva", "Avenida Beija Flor, São Paulo/SP", "Brasileira", "11:00-22:00", 1L);
        var b = Restaurante.existente(1L, "Rafael Brito", "Rua das Rosas, São Paulo/SP", "Contemporanea", "12:00-23:00", 2L);
        var c = Restaurante.existente(2L, "João da Silva", "Avenida Beija Flor, São Paulo/SP", "Brasileira", "11:00-22:00", 1L);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    void validarTamanhoMaximoDosCampos() {
        assertThrows(IllegalArgumentException.class,
                () -> Restaurante.criar("A".repeat(151), "Rua das Rosas, São Paulo/SP", "Brasileira", "10:00-22:00", 1L));
        assertThrows(IllegalArgumentException.class,
                () -> Restaurante.criar("João da Silva", "A".repeat(251), "Brasileira", "10:00-22:00", 1L));
        assertThrows(IllegalArgumentException.class,
                () -> Restaurante.criar("João da Silva", "Rua das Rosas, São Paulo/SP", "A".repeat(101), "10:00-22:00", 1L));
        assertThrows(IllegalArgumentException.class,
                () -> Restaurante.criar("João da Silva", "Rua das Rosas, São Paulo/SP", "Brasileira", "A".repeat(101), 1L));
    }
}
