package br.com.gestrest.api.integration.tipousuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import br.com.gestrest.api.adapter.in.web.dto.response.TipoUsuarioResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TipoUsuarioControllerIT {

    @Autowired
    private TestRestTemplate rest;

    private static Long createdId;

    @Test
    @Order(1)
    void deveCriarTipoUsuario() {

        var request = Map.of(
                "nome", "CLIENTE"
        );

        var response = rest.postForEntity(
                "/api/v1/tipos-usuarios",
                request,
                TipoUsuarioResponse.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        createdId = response.getBody().id();
    }

    @Test
    @Order(2)
    void deveListarTiposUsuarios() {

        var response = rest.getForEntity(
                "/api/v1/tipos-usuarios",
                TipoUsuarioResponse[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(3)
    void deveBuscarPorId() {

        var response = rest.getForEntity(
                "/api/v1/tipos-usuarios/" + createdId,
                TipoUsuarioResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdId, response.getBody().id());
    }

    @Test
    @Order(4)
    void deveAtualizarTipoUsuario() {

        var request = Map.of(
                "nome", "DONO_RESTAURANTE"
        );
        
        System.out.println("ID antes update = " + createdId);
        
        rest.put(
                "/api/v1/tipos-usuarios/" + createdId,
                request
        );

        var response = rest.getForEntity(
                "/api/v1/tipos-usuarios/" + createdId,
                TipoUsuarioResponse.class
        );

        assertEquals("DONO_RESTAURANTE", response.getBody().nome());
    }

    @Test
    @Order(5)
    void deveExcluirTipoUsuario() {

        rest.delete("/api/v1/tipos-usuarios/" + createdId);

        var response = rest.getForEntity(
                "/api/v1/tipos-usuarios/" + createdId,
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
