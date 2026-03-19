package br.com.gestrest.api.application.usecase.impl.restaurante;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.exception.BusinessException;
import br.com.gestrest.api.domain.exception.UsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("Validação de dono de restaurante")
class ValidarDonoRestauranteServiceTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepository;

    @InjectMocks
    private ValidarDonoRestauranteService service;

    @Test
    @DisplayName("Deve validar com sucesso quando usuário existir e for dono")
    void deveValidarComSucessoQuandoUsuarioForDono() {
        var tipoDono = TipoUsuario.existente(1L, "DONO_RESTAURANTE");
        var dono = Usuario.existente(1L, "Rafael Brito", "rafael.brito@gestrest.com", "rafael.brito", "Senha@123", "Rua das Rosas, São Paulo/SP", tipoDono);

        when(usuarioRepository.buscarPorId(1L)).thenReturn(Optional.of(dono));

        assertDoesNotThrow(() -> service.validar(1L));
    }

    @Test
    @DisplayName("Deve falhar quando usuário não existir")
    void deveFalharQuandoUsuarioNaoExistir() {
        when(usuarioRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> service.validar(99L));
    }

    @Test
    @DisplayName("Deve falhar quando usuário não for dono")
    void deveFalharQuandoUsuarioNaoForDono() {
        var tipoCliente = TipoUsuario.existente(2L, "CLIENTE");
        var cliente = Usuario.existente(2L, "José Pereira", "jose.pereira@gestrest.com", "jose.pereira", "Senha@123", "Rua das Rosas, São Paulo/SP", tipoCliente);

        when(usuarioRepository.buscarPorId(2L)).thenReturn(Optional.of(cliente));

        assertThrows(BusinessException.class, () -> service.validar(2L));
    }
}
