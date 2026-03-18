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

import br.com.gestrest.api.domain.exception.EntityNotFoundException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("AtualizarItemCardapioUseCaseImpl Tests")
class AtualizarItemCardapioUseCaseImplTest {

    @Mock
    private ItemCardapioRepositoryPort repository;

    @InjectMocks
    private AtualizarItemCardapioUseCaseImpl useCase;

    @Test
    void atualizarSucesso() {
        var existente = ItemCardapio.existente(5L, "Sanduiche Natural", "Pao integral com frango desfiado e salada", new BigDecimal("18.00"), 2L,
            true, "/itens/sanduiche-natural.jpg");
        var atualizado = ItemCardapio.existente(5L, "Sanduiche Natural Premium", "Pao integral, frango desfiado, ricota e folhas", new BigDecimal("22.50"),
            2L, false, "/itens/sanduiche-natural-premium.jpg");

        when(repository.buscarPorId(5L)).thenReturn(Optional.of(existente));
        when(repository.salvar(any(ItemCardapio.class))).thenReturn(atualizado);

        var result = useCase.atualizar(atualizado);

        assertEquals("Sanduiche Natural Premium", result.getNome());
        assertEquals(0, result.getPreco().compareTo(new BigDecimal("22.50")));
    }

    @Test
    void atualizarNaoEncontrado() {
        var atualizado = ItemCardapio.existente(55L, "Sanduiche Natural Premium", "Pao integral, frango desfiado, ricota e folhas", new BigDecimal("22.50"),
                2L, false, "/itens/sanduiche-natural-premium.jpg");
        when(repository.buscarPorId(55L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> useCase.atualizar(atualizado));
    }
}
