package br.com.gestrest.api.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Usuario testes de dominio")
class UsuarioTest {

    @Test
    @DisplayName("isDono e isCliente devem ser baseados no ID do TipoUsuarioEnum")
    void criarEFlags() {
        var tipoDono = TipoUsuario.existente(TipoUsuarioEnum.DONO_RESTAURANTE.getId(), "DONO_RESTAURANTE");
        var tipoCliente = TipoUsuario.existente(TipoUsuarioEnum.CLIENTE.getId(), "CLIENTE");

        var dono = Usuario.criar("Rafael Brito", "rafael.brito@gestrest.com", "rafael.brito", "Senha@123", "Rua das Rosas, São Paulo/SP", tipoDono);
        var cliente = Usuario.criar("José Pereira", "jose.pereira@gestrest.com", "jose.pereira", "Senha@456", "Rua das Rosas, São Paulo/SP", tipoCliente);

        assertTrue(dono.isDono());
        assertFalse(dono.isCliente());

        assertTrue(cliente.isCliente());
        assertFalse(cliente.isDono());
    }

    @Test
    @DisplayName("alterarTipoUsuario deve refletir no isDono e atualizarDados altera o nome")
    void alterarTipoEAtualizarDados() {
        var tipo = TipoUsuario.existente(TipoUsuarioEnum.CLIENTE.getId(), "CLIENTE");
        var u = Usuario.criar("Rafael Brito", "rafael.brito@gestrest.com", "rafael.brito", "Senha@789", "Rua das Rosas, São Paulo/SP", tipo);
        assertTrue(u.isCliente());

        var novoDono = TipoUsuario.existente(TipoUsuarioEnum.DONO_RESTAURANTE.getId(), "DONO_RESTAURANTE");
        u.alterarTipoUsuario(novoDono);
        assertEquals("DONO_RESTAURANTE", u.getTipoUsuario().getNome());
        assertTrue(u.isDono());
        assertFalse(u.isCliente());

        u.atualizarDados("Rafael Brito");
        assertEquals("Rafael Brito", u.getNome());
    }

    @Test
    @DisplayName("criar() deve inicializar dataCriacao e deixar dataUltimaAlteracao nula")
    void criarDeveSetarDataCriacao() {
        var tipo = TipoUsuario.existente(1L, "CLIENTE");
        var before = LocalDateTime.now().minusSeconds(1);

        var usuario = Usuario.criar("João da Silva", "joao.silva@gestrest.com", "joao.silva", "Senha@111", "Avenida Beija Flor, São Paulo/SP", tipo);

        assertNotNull(usuario.getDataCriacao());
        assertNull(usuario.getDataUltimaAlteracao());
        assertTrue(usuario.getDataCriacao().isAfter(before));
    }

    @Test
    @DisplayName("atualizarDados() deve setar dataUltimaAlteracao")
    void atualizarDadosDeveSetarDataUltimaAlteracao() {
        var tipo = TipoUsuario.existente(1L, "CLIENTE");
        var usuario = Usuario.criar("João da Silva", "joao.silva@gestrest.com", "joao.silva", "Senha@111", "Avenida Beija Flor, São Paulo/SP", tipo);
        var before = LocalDateTime.now().minusSeconds(1);

        usuario.atualizarDados("Ana Atualizada");

        assertNotNull(usuario.getDataUltimaAlteracao());
        assertTrue(usuario.getDataUltimaAlteracao().isAfter(before));
        assertEquals("Ana Atualizada", usuario.getNome());
    }

    @Test
    @DisplayName("atualizar() deve setar dataUltimaAlteracao e atualizar todos os campos")
    void atualizarDeveSetarDataUltimaAlteracao() {
        var tipo = TipoUsuario.existente(1L, "CLIENTE");
        var novoTipo = TipoUsuario.existente(2L, "DONO_RESTAURANTE");
        var usuario = Usuario.criar("João da Silva", "joao.silva@gestrest.com", "joao.silva", "Senha@111", "Rua das Rosas, São Paulo/SP", tipo);
        var before = LocalDateTime.now().minusSeconds(1);

        usuario.atualizar("João da Silva", "joao.silva@gestrest.com", "Rua das Rosas, São Paulo/SP", novoTipo);

        assertNotNull(usuario.getDataUltimaAlteracao());
        assertTrue(usuario.getDataUltimaAlteracao().isAfter(before));
        assertEquals("João da Silva", usuario.getNome());
        assertEquals("joao.silva@gestrest.com", usuario.getEmail());
        assertEquals("Rua das Rosas, São Paulo/SP", usuario.getEndereco());
        assertEquals("DONO_RESTAURANTE", usuario.getTipoUsuario().getNome());
    }

    @Test
    @DisplayName("existente() com datas deve preservar dataCriacao e dataUltimaAlteracao")
    void existenteComDatasDevePreservarCampos() {
        var tipo = TipoUsuario.existente(1L, "CLIENTE");
        var dataCriacao = LocalDateTime.of(2025, 1, 10, 12, 0);
        var dataUltimaAlteracao = LocalDateTime.of(2025, 6, 15, 8, 30);

        var usuario = Usuario.existente(1L, "João da Silva", "joao.silva@gestrest.com", "joao.silva", "Senha@123", "Rua das Rosas, São Paulo/SP", tipo,
                dataCriacao, dataUltimaAlteracao);

        assertEquals(dataCriacao, usuario.getDataCriacao());
        assertEquals(dataUltimaAlteracao, usuario.getDataUltimaAlteracao());
    }

    @Test
    @DisplayName("existente() sem datas deve deixar ambos os campos nulos")
    void existenteSemDatasDeveDeixarCamposNulos() {
        var tipo = TipoUsuario.existente(1L, "CLIENTE");
        var usuario = Usuario.existente(1L, "João da Silva", "joao.silva@gestrest.com", "joao.silva", "Senha@123", "Rua das Rosas, São Paulo/SP", tipo);

        assertNull(usuario.getDataCriacao());
        assertNull(usuario.getDataUltimaAlteracao());
    }
}
