package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RestauranteDomainTest {

    @Test
    void criarValido() {
        var r = Restaurante.criar("João da Silva", "Avenida Beija Flor, São Paulo/SP", "Contemporanea", "Seg-Sab 11:30-22:30", 10L);
        assertEquals("João da Silva", r.getNome());
    }

    @Test
    void criarInvalidoNome() {
        assertThrows(IllegalArgumentException.class, () -> Restaurante.criar(null, "Rua das Rosas, São Paulo/SP", "Brasileira", "Seg-Dom 11:00-23:00", 1L));
    }

    @Test
    void atualizarValido() {
        var r = Restaurante.existente(1L, "José Pereira", "Rua das Rosas, São Paulo/SP", "Italiana", "Ter-Dom 12:00-23:00", 2L);
        r.atualizar("Rafael Brito", "Rua das Rosas, São Paulo/SP", "Italiana Artesanal", "Ter-Dom 12:00-23:30");
        assertEquals("Rafael Brito", r.getNome());
    }
}
