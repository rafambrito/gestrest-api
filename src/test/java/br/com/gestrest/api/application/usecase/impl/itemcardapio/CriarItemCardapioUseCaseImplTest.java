package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.Restaurante;
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

    @Test
    @DisplayName("Deve criar item com sucesso")
    void deveCriarItemComSucesso() {
        // Arrange
        var restaurante = Restaurante.existente(1L, "R", "End", "Tipo", "10:00-22:00", 1L);
        var item = ItemCardapio.criar("Nome", "Desc", new BigDecimal("10.00"), 1L);

        when(restauranteRepository.buscarPorId(1L)).thenReturn(Optional.of(restaurante));
        when(repository.salvar(any(ItemCardapio.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        var criado = useCase.criar(item);

        // Assert
        assertEquals("Nome", criado.getNome());
        assertEquals(0, criado.getPreco().compareTo(new BigDecimal("10.00")));
    }
}