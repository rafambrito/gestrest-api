package br.com.gestrest.api.application.usecase.impl.usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("ListarUsuariosUseCaseImpl Tests")
class ListarUsuariosUseCaseImplTest {

    @Mock
    private UsuarioRepositoryPort repository;

    @InjectMocks
    private ListarUsuariosUseCaseImpl useCase;

    @Test
    @DisplayName("Deve listar usuarios com sucesso")
    void deveListarUsuariosComSucesso() {
        // Arrange
        var tipoUsuario = TipoUsuario.existente(1L, "GERENTE_RESTAURANTE");
        var usuario1 = Usuario.existente(1L, "João da Silva", "joao.silva@gestrest.com", "joao.silva", "Senha@123", "Rua das Rosas, São Paulo/SP", tipoUsuario);
        var usuario2 = Usuario.existente(2L, "José Pereira", "jose.pereira@gestrest.com", "jose.pereira", "Senha@456", "Avenida Beija Flor, São Paulo/SP", tipoUsuario);

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(repository.listar()).thenReturn(usuarios);

        // Act
        var resultado = useCase.executar();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("João da Silva", resultado.get(0).getNome());
        assertEquals("José Pereira", resultado.get(1).getNome());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nao ha usuarios")
    void deveRetornarListaVaziaQuandoNaoHaUsuarios() {
        // Arrange
        when(repository.listar()).thenReturn(List.of());

        // Act
        var resultado = useCase.executar();

        // Assert
        assertTrue(resultado.isEmpty());
    }
}
