package br.com.gestrest.api.application.usecase.impl.restaurante;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.RecursoEmUsoException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExcluirRestauranteUseCaseImpl Tests")
class ExcluirRestauranteUseCaseImplTest {

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    @Mock
    private ItemCardapioRepositoryPort itemRepository;

    @InjectMocks
    private ExcluirRestauranteUseCaseImpl useCase;

    @Test
    @DisplayName("Deve falhar ao excluir restaurante que possui itens")
    void deveFalharAoExcluirQuandoPossuiItens() {
        // Arrange
        var itens = List.of(ItemCardapio.existente(1L, "Pizza", "Desc", new BigDecimal("10.00"), 1L));
        when(itemRepository.listarPorRestauranteId(1L)).thenReturn(itens);

        // Act & Assert
        assertThrows(RecursoEmUsoException.class, () -> useCase.deletar(1L));
    }
}