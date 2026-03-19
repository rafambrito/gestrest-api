package br.com.gestrest.api.application.usecase.impl.tipousuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.DuplicateResourceException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CriarTipoUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioRepositoryPort repository;

    @InjectMocks
    private CriarTipoUsuarioUseCaseImpl useCase;

    @Test
    void deveCriarTipoUsuarioComSucesso() {
        var tipoUsuario = TipoUsuario.criar("FORNECEDOR");
        var tipoUsuarioSalvo = TipoUsuario.existente(1L, "FORNECEDOR");

        when(repository.salvar(any())).thenReturn(tipoUsuarioSalvo);

        var resultado = useCase.criar(tipoUsuario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("FORNECEDOR", resultado.getNome());
        verify(repository).salvar(tipoUsuario);
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioJaExiste() {
        var tipoUsuario = TipoUsuario.criar("CLIENTE");

        when(repository.salvar(any()))
                .thenThrow(new DuplicateResourceException("TipoUsuario com nome 'CLIENTE' já cadastrado"));

        assertThrows(DuplicateResourceException.class, () -> useCase.criar(tipoUsuario));
        verify(repository).salvar(tipoUsuario);
    }

    @Test
    void deveLancarExcecaoQuandoNomeForInvalido() {
        assertThrows(IllegalArgumentException.class, () -> TipoUsuario.criar(null));
        verifyNoInteractions(repository);
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazio() {
        assertThrows(IllegalArgumentException.class, () -> TipoUsuario.criar("  "));
        verifyNoInteractions(repository);
    }
}