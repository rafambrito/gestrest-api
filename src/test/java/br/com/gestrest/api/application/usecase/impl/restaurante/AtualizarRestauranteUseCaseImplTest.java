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
        var dono = Usuario.existente(2L, "Rafael Brito", "rafael.brito@gestrest.com", "rafael.brito", "Senha@123", "Rua das Rosas, São Paulo/SP", donoTipo);
        var existente = Restaurante.existente(5L, "João da Silva", "Rua das Rosas, São Paulo/SP", "Churrascaria", "11:00-23:00", 2L);

        when(restauranteRepository.buscarPorId(5L)).thenReturn(Optional.of(existente));
        when(restauranteRepository.salvar(any(Restaurante.class))).thenAnswer(inv -> inv.getArgument(0));

        var domain = Restaurante.existente(5L, "Rafael Brito", "Rua das Rosas, São Paulo/SP", "Churrascaria Premium", "11:00-23:30", 2L);
        var result = useCase.atualizar(domain);

        assertEquals("Rafael Brito", result.getNome());
    }

    @Test
    void restauranteNaoEncontrado() {
        when(restauranteRepository.buscarPorId(99L)).thenReturn(Optional.empty());
        var domain = Restaurante.existente(99L, "José Pereira", "Rua das Rosas, São Paulo/SP", "Italiana", "12:00-23:00", 1L);
        assertThrows(RestauranteNaoEncontradoException.class, () -> useCase.atualizar(domain));
    }
}