package br.com.gestrest.api.application.usecase.impl.tipousuario;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.application.usecase.impl.tipousuario.ExcluirTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
class ExcluirTipoUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioRepositoryPort repository;

    @InjectMocks
    private ExcluirTipoUsuarioUseCaseImpl useCase;

    @Test
    void deveDeletarTipoUsuarioComSucesso() {
        var tipoUsuarioExistente = TipoUsuario.existente(1L, "CLIENTE");

        when(repository.buscarPorId(1L)).thenReturn(Optional.of(tipoUsuarioExistente));

        useCase.deletar(1L);

        verify(repository).buscarPorId(1L);
        verify(repository).deletar(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarQuandoTipoUsuarioNaoForEncontrado() {
        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.deletar(99L));
        verify(repository).buscarPorId(99L);
    }
}
