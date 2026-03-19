package br.com.gestrest.api.application.usecase.impl.restaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;

@ExtendWith(MockitoExtension.class)
class BuscarRestaurantePorIdUseCaseTest {

    @Mock
    private RestauranteRepositoryPort repository;

    private BuscarRestaurantePorIdUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        useCase = new BuscarRestaurantePorIdUseCaseImpl(repository);
    }

    @Test
    void buscar_sucesso() {
        var r = Restaurante.existente(2L, "José Pereira", "Rua Treze de Maio, 902 - Bela Vista, Sao Paulo/SP", "Italiana", "Ter-Dom 12:00-23:30", 1L);
        when(repository.buscarPorId(2L)).thenReturn(Optional.of(r));

        Restaurante res = useCase.executar(2L);
        assertEquals(r.getNome(), res.getNome());
    }

    @Test
    void buscar_nao_encontrado() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());
        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.executar(99L));
    }
}
