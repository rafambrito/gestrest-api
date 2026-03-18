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
        var restaurante = Restaurante.criar("Rafael Brito", "Rua das Rosas, São Paulo/SP", "Brasileira", "Seg-Sab 11:00-22:00", 5L);
        var dono = Usuario.existente(5L, "Rafael Brito", "rafael.brito@gestrest.com", "rafael.brito", "Senha@123", "Rua das Rosas, São Paulo/SP", null);
        // isDono no dominio valida pelo ID do enum DONO_RESTAURANTE (id=1)
        var tipo = br.com.gestrest.api.domain.model.TipoUsuario.existente(1L, "DONO_RESTAURANTE");
        dono.alterarTipoUsuario(tipo);

        when(usuarioRepository.buscarPorId(5L)).thenReturn(Optional.of(dono));
        when(repository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        Restaurante r = useCase.criar(restaurante);
        assertEquals(restaurante.getNome(), r.getNome());
    }

    @Test
    void criar_dono_inexistente() {
        var restaurante = Restaurante.criar("João da Silva", "Rua das Rosas, São Paulo/SP", "Mediterranea", "Ter-Dom 12:00-23:00", 7L);
        when(usuarioRepository.buscarPorId(7L)).thenReturn(Optional.empty());
        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.criar(restaurante));
    }

    @Test
    void criar_usuario_nao_e_dono() {
        var restaurante = Restaurante.criar("José Pereira", "Rua das Rosas, São Paulo/SP", "Italiana", "Seg-Dom 11:30-23:30", 8L);
        var usuario = Usuario.existente(8L, "José Pereira", "jose.pereira@gestrest.com", "jose.pereira", "Senha@456", "Rua das Rosas, São Paulo/SP", null);
        // set tipo different from DONO_RESTAURANTE
        var tipo = br.com.gestrest.api.domain.model.TipoUsuario.existente(2L, "CLIENTE");
        usuario.alterarTipoUsuario(tipo);

        when(usuarioRepository.buscarPorId(8L)).thenReturn(Optional.of(usuario));

        assertThrows(BusinessException.class, () -> useCase.criar(restaurante));
    }
}