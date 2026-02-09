package br.com.gestrest.application.tipousuario.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.application.usecase.impl.tipousuario.CriarTipoUsuarioUseCaseImpl;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CriarTipoUsuarioUseCaseImplTest {

    @Mock
    private TipoUsuarioRepositoryPort repository;

    @InjectMocks
    private CriarTipoUsuarioUseCaseImpl useCase;

    @Test
    void deveCriarTipoUsuario() {

        var tipo = TipoUsuario.criar("CLIENTE");

        when(repository.salvar(any()))
                .thenReturn(TipoUsuario.existente(1L, "CLIENTE"));

        var result = useCase.executar(tipo);

        assertEquals("CLIENTE", result.getNome());
    }
}
