package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.EntityNotFoundException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("BuscarItemCardapioPorIdUseCaseImpl Tests")
class BuscarItemCardapioPorIdUseCaseImplTest {

    @Mock
    private ItemCardapioRepositoryPort repository;

    @InjectMocks
    private BuscarItemCardapioPorIdUseCaseImpl useCase;

    @Test
    void buscarSucesso() {
        var item = ItemCardapio.existente(10L, "Nome", "Desc", new BigDecimal("5.00"), 2L);
        when(repository.buscarPorId(10L)).thenReturn(Optional.of(item));

        var resultado = useCase.buscarPorId(10L);

        assertEquals(10L, resultado.getId());
        assertEquals("Nome", resultado.getNome());
    }

    @Test
    void buscarNaoEncontrado() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> useCase.buscarPorId(99L));
    }
}
