package br.com.gestrest.api.application.usecase.impl.restaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.BusinessException;
import br.com.gestrest.api.domain.exception.UsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CriarRestauranteUseCaseTest {

    @Mock
    private RestauranteRepositoryPort repository;

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    private CriarRestauranteUseCaseImpl useCase;

    @BeforeEach
    void setup() {
        useCase = new CriarRestauranteUseCaseImpl(repository, usuarioRepository);
    }

    @Test
    void criar_sucesso() {
        var restaurante = Restaurante.criar("N", "E", "T", "H", 5L);
        var dono = Usuario.existente(5L, "D", "d@x.com", "dlog", "s", "end", null);
        // make as dono
        dono.alterarTipoUsuario(null);
        // but isDono checks tipoUsuario name; create tipo that makes it dono
        var tipo = br.com.gestrest.api.domain.model.TipoUsuario.existente(9L, "DONO_RESTAURANTE");
        dono.alterarTipoUsuario(tipo);

        when(usuarioRepository.buscarPorId(5L)).thenReturn(Optional.of(dono));
        when(repository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        Restaurante r = useCase.criar(restaurante);
        assertEquals(restaurante.getNome(), r.getNome());
    }

    @Test
    void criar_dono_inexistente() {
        var restaurante = Restaurante.criar("N", "E", "T", "H", 7L);
        when(usuarioRepository.buscarPorId(7L)).thenReturn(Optional.empty());
        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.criar(restaurante));
    }

    @Test
    void criar_usuario_nao_e_dono() {
        var restaurante = Restaurante.criar("N", "E", "T", "H", 8L);
        var usuario = Usuario.existente(8L, "U", "u@x.com", "ulog", "s", "end", null);
        // set tipo different from DONO_RESTAURANTE
        var tipo = br.com.gestrest.api.domain.model.TipoUsuario.existente(2L, "CLIENTE");
        usuario.alterarTipoUsuario(tipo);

        when(usuarioRepository.buscarPorId(8L)).thenReturn(Optional.of(usuario));

        assertThrows(BusinessException.class, () -> useCase.criar(restaurante));
    }
}