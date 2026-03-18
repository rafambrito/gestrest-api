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

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.RestauranteResponse;
import java.util.List;

/**
 * Documentação da API de Restaurantes.
 * 
 * Define as especificações técnicas para gerenciamento de restaurantes no sistema.
 * Cada restaurante é gerenciado por um usuário (dono) e possui um cardápio.
 */
@Tag(
    name = "Restaurantes",
    description = "API para gerenciamento de restaurantes, incluindo tipos de cozinha e horários"
)
public interface RestauranteControllerDoc {

    @Operation(
        summary = "Criar novo restaurante",
        description = "Cria um novo restaurante no sistema. Um restaurante deve ter um dono (usuário) associado e informações como tipo de cozinha e horário de funcionamento",
        tags = {"Restaurantes"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Restaurante criado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RestauranteResponse.class),
                examples = @ExampleObject(
                    name = "Exemplo de Restaurante Criado",
                    value = "{\"id\": 1, \"nome\": \"Pizza House\", \"endereco\": \"Rua das Flores 123\", \"tipoCozinha\": \"Italiana\", \"horarioFuncionamento\": \"11:00 - 22:00\", \"donoId\": 1}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validação falhou - campos obrigatórios: nome, endereco, tipoCozinha, horarioFuncionamento, donoId"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflito - restaurante já existe ou dono inválido"
        )
    })
    ResponseEntity<RestauranteResponse> criar(
        @Valid @RequestBody(
            description = "Dados do restaurante",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CriarRestauranteRequest.class),
                examples = @ExampleObject(
                    name = "Criar Restaurante",
                    value = "{\"nome\": \"Pizza House\", \"endereco\": \"Rua das Flores 123\", \"tipoCozinha\": \"Italiana\", \"horarioFuncionamento\": \"11:00 - 22:00\", \"donoId\": 1}"
                )
            )
        ) CriarRestauranteRequest request
    );

    @Operation(
        summary = "Buscar restaurante por ID",
        description = "Retorna os detalhes de um restaurante específico incluindo tipo de cozinha, horário e informações do dono",
        tags = {"Restaurantes"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Restaurante encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RestauranteResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Restaurante não encontrado"
        )
    })
    ResponseEntity<RestauranteResponse> buscar(
        @Parameter(
            name = "id",
            description = "ID do restaurante",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    );

    @Operation(
        summary = "Listar todos os restaurantes",
        description = "Retorna uma lista com todos os restaurantes cadastrados no sistema com suas informações completas",
        tags = {"Restaurantes"}
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de restaurantes",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RestauranteResponse.class)
        )
    )
    ResponseEntity<List<RestauranteResponse>> listar();

    @Operation(
        summary = "Atualizar restaurante",
        description = "Atualiza os dados de um restaurante existente como nome, endereço, tipo de cozinha e horário de funcionamento",
        tags = {"Restaurantes"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Restaurante atualizado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RestauranteResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validação falhou - campos obrigatórios: nome, endereco, tipoCozinha, horarioFuncionamento, donoId"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Restaurante não encontrado"
        )
    })
    ResponseEntity<RestauranteResponse> atualizar(
        @Parameter(
            name = "id",
            description = "ID do restaurante",
            required = true,
            example = "1"
        )
        @PathVariable Long id,
        @Valid @RequestBody(
            description = "Novos dados do restaurante",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Atualizar Restaurante",
                    value = "{\"nome\": \"Pizza House Premium\", \"endereco\": \"Rua das Flores 456\", \"tipoCozinha\": \"Italiana Moderna\", \"horarioFuncionamento\": \"12:00 - 23:00\", \"donoId\": 1}"
                )
            )
        ) AtualizarRestauranteRequest request
    );

    @Operation(
        summary = "Deletar restaurante",
        description = "Remove um restaurante do sistema. Cuidado: verifique integridade referencial antes de deletar (itens do cardápio, pedidos)",
        tags = {"Restaurantes"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Restaurante deletado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Restaurante não encontrado"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflito - restaurante possui itens do cardápio ou pedidos associados"
        )
    })
    ResponseEntity<Void> deletar(
        @Parameter(
            name = "id",
            description = "ID do restaurante",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    );
}
