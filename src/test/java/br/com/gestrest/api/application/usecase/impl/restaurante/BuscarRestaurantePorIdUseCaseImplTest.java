package br.com.gestrest.api.application.usecase.impl.restaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("BuscarRestaurantePorIdUseCaseImpl Testes")
class BuscarRestaurantePorIdUseCaseImplTest {

    @Mock
    private RestauranteRepositoryPort repository;

    @InjectMocks
    private BuscarRestaurantePorIdUseCaseImpl useCase;

    @Test
    @DisplayName("Deve retornar restaurante quando existir")
    void deveRetornarRestaurante() {
        var r = Restaurante.existente(1L, "João da Silva", "Avenida Beija Flor, São Paulo/SP", "Contemporanea", "10:00-22:00", 1L);

        when(repository.buscarPorId(1L)).thenReturn(Optional.of(r));

        var resultado = useCase.executar(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("João da Silva", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lancar excecao quando restaurante nao encontrado")
    void deveLancarQuandoNaoEncontrado() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.executar(99L));
    }
}
