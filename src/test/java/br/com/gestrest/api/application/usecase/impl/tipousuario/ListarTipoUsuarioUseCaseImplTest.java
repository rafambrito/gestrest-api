package br.com.gestrest.api.application.usecase.impl.tipousuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.application.usecase.impl.tipousuario.ListarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
class ListarTipoUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioRepositoryPort repository;

    @InjectMocks
    private ListarTipoUsuarioUseCaseImpl useCase;

    @Test
    void deveRetornarListaDeTiposDeUsuario() {
        var tipos = List.of(
                TipoUsuario.existente(1L, "DONO_RESTAURANTE"),
                TipoUsuario.existente(2L, "CLIENTE")
        );

        when(repository.listar()).thenReturn(tipos);

        var resultado = useCase.listar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("DONO_RESTAURANTE", resultado.get(0).getNome());
        assertEquals("CLIENTE", resultado.get(1).getNome());
        verify(repository).listar();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverDados() {
        when(repository.listar()).thenReturn(Collections.emptyList());

        var resultado = useCase.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository).listar();
    }
}
