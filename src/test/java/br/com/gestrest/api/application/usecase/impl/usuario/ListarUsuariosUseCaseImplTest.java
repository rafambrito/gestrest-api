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
        var tipoUsuario = TipoUsuario.existente(1L, "Admin");
        var usuario1 = Usuario.existente(1L, "João", "joao@test.com", "joao", "senha", "Rua A", tipoUsuario);
        var usuario2 = Usuario.existente(2L, "Maria", "maria@test.com", "maria", "senha", "Rua B", tipoUsuario);

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);
        when(repository.listar()).thenReturn(usuarios);

        // Act
        var resultado = useCase.executar();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
        assertEquals("Maria", resultado.get(1).getNome());
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
