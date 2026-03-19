package br.com.gestrest.api.application.usecase.impl.tipousuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.application.usecase.impl.tipousuario.AtualizarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
class AtualizarTipoUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioRepositoryPort repository;

    @InjectMocks
    private AtualizarTipoUsuarioUseCaseImpl useCase;

    @Test
    void deveAtualizarTipoUsuarioComSucesso() {
        var tipoUsuarioAtualizado = TipoUsuario.existente(1L, "FORNECEDOR_ATUALIZADO");
        var tipoUsuarioExistente = TipoUsuario.existente(1L, "FORNECEDOR");
        var tipoUsuarioSalvo = TipoUsuario.existente(1L, "FORNECEDOR_ATUALIZADO");

        when(repository.buscarPorId(1L)).thenReturn(Optional.of(tipoUsuarioExistente));
        when(repository.salvar(any())).thenReturn(tipoUsuarioSalvo);

        var resultado = useCase.atualizar(tipoUsuarioAtualizado);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("FORNECEDOR_ATUALIZADO", resultado.getNome());
        verify(repository).buscarPorId(1L);
        verify(repository).salvar(tipoUsuarioExistente);
    }

    @Test
    void deveLancarExcecaoAoAtualizarQuandoTipoUsuarioNaoEncontrado() {
        var tipoUsuarioAtualizado = TipoUsuario.existente(99L, "INEXISTENTE");

        when(repository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(TipoUsuarioNaoEncontradoException.class, () -> useCase.atualizar(tipoUsuarioAtualizado));
        verify(repository).buscarPorId(99L);
    }
}
