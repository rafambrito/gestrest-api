package br.com.gestrest.api.application.usecase.impl.itemcardapio;

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

import br.com.gestrest.api.adapter.in.web.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("CriarItemCardapioUseCaseImpl Tests")
class CriarItemCardapioUseCaseImplTest {

    @Mock
    private ItemCardapioRepositoryPort repository;

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    @InjectMocks
    private CriarItemCardapioUseCaseImpl useCase;

    @Test
    @DisplayName("Deve falhar quando restaurante não existir")
    void deveFalharQuandoRestauranteNaoExistir() {
        // Arrange
        var item = ItemCardapio.existente(null, "Pizza", "Deliciosa", new BigDecimal("45.50"), 999L);

        when(restauranteRepository.buscarPorId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.criar(item));
    }
}
