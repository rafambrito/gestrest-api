package br.com.gestrest.api.application.usecase.impl.tipousuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.application.usecase.impl.tipousuario.BuscarTipoUsuarioPorIdUseCaseImpl;
import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
class BuscarTipoUsuarioPorIdUseCaseImplTest {

    @Mock
    private TipoUsuarioRepositoryPort repository;

    @InjectMocks
    private BuscarTipoUsuarioPorIdUseCaseImpl useCase;

    @Test
    void deveBuscarTipoUsuarioPorIdComSucesso() {
        var tipoUsuarioExistente = TipoUsuario.existente(1L, "DONO_RESTAURANTE");

        when(repository.buscarPorId(1L)).thenReturn(Optional.of(tipoUsuarioExistente));

        var resultado = useCase.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("DONO_RESTAURANTE", resultado.getNome());
        verify(repository).buscarPorId(1L);
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoForEncontrado() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.buscarPorId(99L));
        verify(repository).buscarPorId(99L);
    }
}
