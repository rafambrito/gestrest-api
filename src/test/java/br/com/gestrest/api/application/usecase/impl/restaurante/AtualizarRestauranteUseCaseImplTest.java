package br.com.gestrest.api.application.usecase.impl.restaurante;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.RestauranteNaoEncontradoException;
import br.com.gestrest.api.domain.exception.BusinessException;
import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("AtualizarRestauranteUseCaseImpl Tests")
class AtualizarRestauranteUseCaseImplTest {

    @Mock
    private RestauranteRepositoryPort restauranteRepository;

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @InjectMocks
    private AtualizarRestauranteUseCaseImpl useCase;

    @Test
    void atualizarSucesso() {
        var donoTipo = TipoUsuario.existente(1L, "DONO_RESTAURANTE");
        var dono = Usuario.existente(2L, "Dono", "d@d.com", "d", "s", "e", donoTipo);
        var existente = Restaurante.existente(5L, "OldName", "E", "T", "H", 2L);

        when(restauranteRepository.buscarPorId(5L)).thenReturn(Optional.of(existente));
        when(restauranteRepository.salvar(any(Restaurante.class))).thenAnswer(inv -> inv.getArgument(0));

        var domain = Restaurante.existente(5L, "NewName", "NE", "NT", "NH", 2L);
        var result = useCase.atualizar(domain);

        assertEquals("NewName", result.getNome());
    }

    @Test
    void restauranteNaoEncontrado() {
        when(restauranteRepository.buscarPorId(99L)).thenReturn(Optional.empty());
        var domain = Restaurante.existente(99L, "N", "E", "T", "H", 1L);
        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.atualizar(domain));
    }
}