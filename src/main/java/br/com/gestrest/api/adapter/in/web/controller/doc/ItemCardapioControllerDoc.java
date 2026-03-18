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

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.ItemCardapioResponse;
import java.util.List;

/**
 * Documentação da API de Itens do Cardápio.
 * 
 * Define as especificações técnicas para gerenciamento de itens do cardápio de cada restaurante.
 * Os itens contêm informações como nome, descrição, preço e disponibilidade.
 */
@Tag(
    name = "Itens do Cardápio",
    description = "API para gerenciamento de itens do cardápio dos restaurantes"
)
public interface ItemCardapioControllerDoc {

    @Operation(
        summary = "Criar novo item do cardápio",
        description = "Cria um novo item no cardápio de um restaurante. Cada item contém nome, descrição, preço, disponibilidade apenas no local, caminho da foto e informações do restaurante",
        tags = {"Itens do Cardápio"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Item do cardápio criado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemCardapioResponse.class),
                examples = @ExampleObject(
                    name = "Exemplo de Item Criado",
                    value = "{\"id\": 1, \"nome\": \"Pizza Margherita\", \"descricao\": \"Pizza clássica com mozzarela e tomate\", \"preco\": 45.50, \"disponivelSomenteNoLocal\": true, \"fotoPath\": \"/imagens/itens/pizza-margherita.jpg\", \"restauranteId\": 1}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validação falhou - campos obrigatórios: nome, descricao, preco > 0, disponivelSomenteNoLocal, fotoPath, restauranteId"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflito - restaurante não existe ou item já existe"
        )
    })
    ResponseEntity<ItemCardapioResponse> criar(
        @Valid @RequestBody(
            description = "Dados do item do cardápio",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CriarItemCardapioRequest.class),
                examples = @ExampleObject(
                    name = "Criar Item do Cardápio",
                    value = "{\"nome\": \"Pizza Margherita\", \"descricao\": \"Pizza clássica com mozzarela e tomate\", \"preco\": 45.50, \"disponivelSomenteNoLocal\": true, \"fotoPath\": \"/imagens/itens/pizza-margherita.jpg\", \"restauranteId\": 1}"
                )
            )
        ) CriarItemCardapioRequest request
    );

    @Operation(
        summary = "Buscar item do cardápio por ID",
        description = "Retorna os detalhes de um item específico do cardápio incluindo nome, descrição, preço, disponibilidade no local, caminho da foto e qual restaurante pertence",
        tags = {"Itens do Cardápio"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Item encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemCardapioResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Item do cardápio não encontrado"
        )
    })
    ResponseEntity<ItemCardapioResponse> buscar(
        @Parameter(
            name = "id",
            description = "ID do item do cardápio",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    );

    @Operation(
        summary = "Listar itens do cardápio por restaurante",
        description = "Retorna uma lista com todos os itens cadastrados para um restaurante específico",
        tags = {"Itens do Cardápio"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de itens do cardápio",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemCardapioResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Restaurante não encontrado"
        )
    })
    ResponseEntity<List<ItemCardapioResponse>> listarPorRestaurante(
        @Parameter(
            name = "restauranteId",
            description = "ID do restaurante",
            required = true,
            example = "1"
        )
        @PathVariable Long restauranteId
    );

    @Operation(
        summary = "Atualizar item do cardápio",
        description = "Atualiza os dados de um item existente como nome, descrição, preço, disponibilidade no local e caminho da foto",
        tags = {"Itens do Cardápio"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Item atualizado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemCardapioResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validação falhou - campos obrigatórios: nome, descricao, preco > 0, disponivelSomenteNoLocal, fotoPath, restauranteId"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Item do cardápio não encontrado"
        )
    })
    ResponseEntity<ItemCardapioResponse> atualizar(
        @Parameter(
            name = "id",
            description = "ID do item do cardápio",
            required = true,
            example = "1"
        )
        @PathVariable Long id,
        @Valid @RequestBody(
            description = "Novos dados do item do cardápio",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Atualizar Item",
                    value = "{\"nome\": \"Pizza Margherita Premium\", \"descricao\": \"Pizza clássica com mozzarela di bufala e tomate\", \"preco\": 55.50, \"disponivelSomenteNoLocal\": false, \"fotoPath\": \"/imagens/itens/pizza-margherita-premium.jpg\", \"restauranteId\": 1}"
                )
            )
        ) AtualizarItemCardapioRequest request
    );

    @Operation(
        summary = "Deletar item do cardápio",
        description = "Remove um item do cardápio do sistema. Cuidado: verifique se o item não está em pedidos ativas",
        tags = {"Itens do Cardápio"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Item deletado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Item do cardápio não encontrado"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Conflito - item possui pedidos associados"
        )
    })
    ResponseEntity<Void> deletar(
        @Parameter(
            name = "id",
            description = "ID do item do cardápio",
            required = true,
            example = "1"
        )
        @PathVariable Long id
    );
}
