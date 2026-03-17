package br.com.gestrest.api.application.usecase.impl.itemcardapio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.model.ItemCardapio;
import br.com.gestrest.api.domain.model.ports.out.ItemCardapioRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CriarItemCardapioUseCaseTest {

    @Mock
    private ItemCardapioRepositoryPort repository;

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    private CriarItemCardapioUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        useCase = new CriarItemCardapioUseCaseImpl(repository, restauranteRepository);
    }

    @Test
    void criar_sucesso() {
        var item = ItemCardapio.criar("N", "D", BigDecimal.TEN, 3L);
        when(restauranteRepository.buscarPorId(3L)).thenReturn(Optional.of(br.com.gestrest.api.domain.model.Restaurante.existente(3L, "R", "E", "T", "H", 1L)));
        when(repository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        ItemCardapio res = useCase.criar(item);
        assertEquals(item.getNome(), res.getNome());
    }

    @Test
    void criar_restaurante_inexistente() {
        var item = ItemCardapio.criar("N", "D", BigDecimal.TEN, 44L);
        when(restauranteRepository.buscarPorId(44L)).thenReturn(Optional.empty());
        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.criar(item));
    }
}
