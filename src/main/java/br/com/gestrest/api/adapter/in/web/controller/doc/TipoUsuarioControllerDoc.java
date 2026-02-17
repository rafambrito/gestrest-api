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

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarTipoUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarTipoUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.TipoUsuarioResponse;
import java.util.List;

/**
 * Documentação da API de Tipos de Usuário.
 * 
 * Define as especificações técnicas para gerenciamento de tipos de usuários no sistema.
 * Tipos de usuários categorizam usuários como "Cliente" ou "Dono de Restaurante".
 */
@Tag(
    name = "Tipos de Usuário",
    description = "API para gerenciamento de tipos de usuários (Cliente, Dono de Restaurante, etc)"
)
public interface TipoUsuarioControllerDoc {

    @Operation(
        summary = "Criar novo tipo de usuário",
        description = "Cria um novo tipo de usuário no sistema. Exemplo: 'Cliente', 'Dono de Restaurante'",
        tags = {"Tipos de Usuário"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Tipo de usuário criado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TipoUsuarioResponse.class),
                examples = @ExampleObject(
                    name = "Exemplo de Tipo de Usuário Criado",
                    value = "{\"id\": 1, \"nome\": \"Cliente\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validação falhou - nome obrigatório e não pode estar vazio"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflito - tipo de usuário já existe"
        )
    })
    ResponseEntity<TipoUsuarioResponse> criar(
        @Valid @RequestBody(
            description = "Dados do tipo de usuário",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CriarTipoUsuarioRequest.class),
                examples = @ExampleObject(
                    name = "Criar Tipo de Usuário",
                    value = "{\"nome\": \"Cliente\"}"
                )
            )
        ) CriarTipoUsuarioRequest request
    );

    @Operation(
        summary = "Buscar tipo de usuário por ID",
        description = "Retorna os detalhes de um tipo de usuário específico",
        tags = {"Tipos de Usuário"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tipo de usuário encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TipoUsuarioResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tipo de usuário não encontrado"
        )
    })
    ResponseEntity<TipoUsuarioResponse> buscar(
        @Parameter(
            name = "id",
            description = "ID do tipo de usuário",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    );

    @Operation(
        summary = "Listar todos os tipos de usuário",
        description = "Retorna uma lista com todos os tipos de usuários cadastrados no sistema",
        tags = {"Tipos de Usuário"}
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de tipos de usuário",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = TipoUsuarioResponse.class)
        )
    )
    ResponseEntity<List<TipoUsuarioResponse>> listar();

    @Operation(
        summary = "Atualizar tipo de usuário",
        description = "Atualiza os dados de um tipo de usuário existente",
        tags = {"Tipos de Usuário"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tipo de usuário atualizado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TipoUsuarioResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validação falhou"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tipo de usuário não encontrado"
        )
    })
    ResponseEntity<TipoUsuarioResponse> atualizar(
        @Parameter(
            name = "id",
            description = "ID do tipo de usuário",
            required = true,
            example = "1"
        )
        @PathVariable Long id,
        @Valid @RequestBody(
            description = "Novos dados do tipo de usuário",
            required = true
        ) AtualizarTipoUsuarioRequest request
    );

    @Operation(
        summary = "Deletar tipo de usuário",
        description = "Remove um tipo de usuário do sistema. Cuidado: verifique integridade referencial antes de deletar",
        tags = {"Tipos de Usuário"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Tipo de usuário deletado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tipo de usuário não encontrado"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflito - tipo de usuário possui usuários associados"
        )
    })
    ResponseEntity<Void> deletar(
        @Parameter(
            name = "id",
            description = "ID do tipo de usuário",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    );
}
