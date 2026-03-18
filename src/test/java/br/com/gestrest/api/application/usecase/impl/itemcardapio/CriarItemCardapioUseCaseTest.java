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
        var item = ItemCardapio.criar("José Pereira", "Nhoque artesanal com molho de tomate e parmesao", BigDecimal.TEN, 3L,
            false, "/itens/nhoque.jpg");
        when(restauranteRepository.buscarPorId(3L)).thenReturn(Optional.of(br.com.gestrest.api.domain.model.Restaurante.existente(3L, "José Pereira", "Rua das Rosas, São Paulo/SP", "Italiana", "Seg-Dom 11:30-23:30", 1L)));
        when(repository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        ItemCardapio res = useCase.criar(item);
        assertEquals(item.getNome(), res.getNome());
    }

    @Test
    void criar_restaurante_inexistente() {
        var item = ItemCardapio.criar("José Pereira", "Nhoque artesanal com molho de tomate e parmesao", BigDecimal.TEN, 44L,
                true, "/itens/nhoque.jpg");
        when(restauranteRepository.buscarPorId(44L)).thenReturn(Optional.empty());
        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.criar(item));
    }
}
