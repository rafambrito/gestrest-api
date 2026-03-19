package br.com.gestrest.api.application.usecase.impl.restaurante;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.BusinessException;
import br.com.gestrest.api.domain.exception.UsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("Criar Restaurante Caso de Uso")
class CriarRestauranteUseCaseImplTest {

    @Mock
    private RestauranteRepositoryPort repository;

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @InjectMocks
    private CriarRestauranteUseCaseImpl useCase;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = Restaurante.criar(
                "João da Silva",
                "Avenida Beija Flor, São Paulo/SP",
                "Italiana",
                "Seg-Dom 11:00-22:30",
                1L
        );
    }

    @Test
    @DisplayName("Deve criar restaurante com sucesso")
    void devecriarRestauranteComSucesso() {
        Restaurante restauranteSalvo = Restaurante.existente(
                1L,
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamento(),
                restaurante.getDonoId()
        );
        when(repository.salvar(any(Restaurante.class))).thenReturn(restauranteSalvo);

        var usuario = Usuario.existente(1L, "Rafael Brito", "rafael.brito@gestrest.com", "rafael.brito", "Senha@123", "Rua das Rosas, São Paulo/SP", TipoUsuario.existente(1L, "DONO_RESTAURANTE"));
        when(usuarioRepository.buscarPorId(restaurante.getDonoId())).thenReturn(Optional.of(usuario));

        Restaurante resultado = useCase.criar(restaurante);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João da Silva", resultado.getNome());
        assertEquals("Avenida Beija Flor, São Paulo/SP", resultado.getEndereco());
        assertEquals("Italiana", resultado.getTipoCozinha());
        assertEquals("Seg-Dom 11:00-22:30", resultado.getHorarioFuncionamento());
        verify(repository, times(1)).salvar(any(Restaurante.class));
    }

    @Test
    @DisplayName("Deve falhar ao criar restaurante com nome null")
    void devefalharAoCriarComNomeNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Restaurante.criar(null, "Rua", "Italiana", "11:00", 1L)
        );
    }

    @Test
    @DisplayName("Deve falhar ao criar restaurante com nome vazio")
    void devefalharAoCriarComNomeVazio() {
        assertThrows(IllegalArgumentException.class, () ->
                Restaurante.criar("", "Rua", "Italiana", "11:00", 1L)
        );
    }

    @Test
    @DisplayName("Deve falhar ao criar restaurante com endereco null")
    void devefalharAoCriarComEnderecoNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Restaurante.criar("João da Silva", null, "Italiana", "11:00", 1L)
        );
    }

    @Test
    @DisplayName("Deve falhar ao criar restaurante com tipo cozinha null")
    void devefalharAoCriarComTipoCozinhaNUll() {
        assertThrows(IllegalArgumentException.class, () ->
                Restaurante.criar("João da Silva", "Rua", null, "11:00", 1L)
        );
    }

    @Test
    @DisplayName("Deve falhar ao criar restaurante com horario funcionamento null")
    void devefalharAoCriarComHorarioNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Restaurante.criar("João da Silva", "Rua", "Italiana", null, 1L)
        );
    }

    @Test
    @DisplayName("Deve falhar ao criar restaurante com dono null")
    void devefalharAoCriarComDonoNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Restaurante.criar("João da Silva", "Rua", "Italiana", "11:00", null)
        );
    }

    @Test
    @DisplayName("Deve falhar quando dono nao encontrado")
    void deveFalharQuandoDonoNaoEncontrado() {
        when(usuarioRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        var r = Restaurante.criar("José Pereira", "Rua das Rosas, São Paulo/SP", "Italiana", "10:00-22:00", 99L);

        assertThrows(UsuarioNaoEncontradoException.class, () -> useCase.criar(r));
    }

    @Test
    @DisplayName("Deve falhar quando usuario nao for dono")
    void deveFalharQuandoUsuarioNaoForDono() {
        var tipoCliente = TipoUsuario.existente(2L, "CLIENTE");
        var cliente = Usuario.existente(11L, "José Pereira", "jose.pereira@gestrest.com", "jose.pereira", "Senha@234", "Rua das Rosas, São Paulo/SP", tipoCliente);

        var r = Restaurante.criar("José Pereira", "Rua das Rosas, São Paulo/SP", "Italiana", "10:00-22:00", 11L);

        when(usuarioRepository.buscarPorId(11L)).thenReturn(Optional.of(cliente));

        assertThrows(BusinessException.class, () -> useCase.criar(r));
    }
}