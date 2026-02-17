package br.com.gestrest.api.adapter.in.web.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.UsuarioResponse;
import java.util.List;

/**
 * Documentação da API de Usuários.
 * 
 * Define as especificações técnicas para gerenciamento de usuários no sistema.
 * Usuários podem ser clientes ou donos de restaurantes conforme seu tipo.
 */
@Tag(
    name = "Usuários",
    description = "API para gerenciamento de usuários (Clientes e Donos de Restaurante)"
)
public interface UsuarioControllerDoc {

    @Operation(
        summary = "Criar novo usuário",
        description = "Cria um novo usuário no sistema com tipo associado. Um usuário pode ser Cliente ou Dono de Restaurante",
        tags = {"Usuários"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuário criado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponse.class),
                examples = @ExampleObject(
                    name = "Exemplo de Usuário Criado",
                    value = "{\"id\": 1, \"nome\": \"João Silva\", \"email\": \"joao@email.com\", \"login\": \"joao123\", \"endereco\": \"Rua ABC 123\", \"tipoUsuario\": {\"id\": 1, \"nome\": \"Cliente\"}}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validação falhou - campos obrigatórios: nome, email válido, login, senha, tipoUsuarioId"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflito - email ou login já existe no sistema"
        )
    })
    ResponseEntity<UsuarioResponse> criar(
        @Valid @RequestBody(
            description = "Dados do usuário",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CriarUsuarioRequest.class),
                examples = @ExampleObject(
                    name = "Criar Usuário",
                    value = "{\"nome\": \"João Silva\", \"email\": \"joao@email.com\", \"login\": \"joao123\", \"senha\": \"senha123\", \"endereco\": \"Rua ABC 123\", \"tipoUsuarioId\": 1}"
                )
            )
        ) CriarUsuarioRequest request
    );

    @Operation(
        summary = "Buscar usuário por ID",
        description = "Retorna os detalhes de um usuário específico incluindo seu tipo",
        tags = {"Usuários"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado"
        )
    })
    ResponseEntity<UsuarioResponse> buscar(
        @Parameter(
            name = "id",
            description = "ID do usuário",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    );

    @Operation(
        summary = "Listar todos os usuários",
        description = "Retorna uma lista com todos os usuários cadastrados no sistema com seus tipos",
        tags = {"Usuários"}
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de usuários",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UsuarioResponse.class)
        )
    )
    ResponseEntity<List<UsuarioResponse>> listar();

    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza os dados de um usuário existente. Apenas o nome pode ser atualizado",
        tags = {"Usuários"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário atualizado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validação falhou - nome obrigatório"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado"
        )
    })
    ResponseEntity<UsuarioResponse> atualizar(
        @Parameter(
            name = "id",
            description = "ID do usuário",
            required = true,
            example = "1"
        )
        @PathVariable Long id,
        @Valid @RequestBody(
            description = "Novos dados do usuário",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Atualizar Usuário",
                    value = "{\"nome\": \"João Silva Atualizado\"}"
                )
            )
        ) AtualizarUsuarioRequest request
    );

    @Operation(
        summary = "Deletar usuário",
        description = "Remove um usuário do sistema. Cuidado: verifique integridade referencial antes de deletar",
        tags = {"Usuários"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Usuário deletado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflito - usuário possui restaurante ou pedidos associados"
        )
    })
    ResponseEntity<Void> deletar(
        @Parameter(
            name = "id",
            description = "ID do usuário",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    );
}
