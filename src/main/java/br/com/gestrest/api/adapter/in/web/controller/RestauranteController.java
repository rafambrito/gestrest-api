package br.com.gestrest.api.adapter.in.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestrest.api.adapter.in.web.controller.doc.RestauranteControllerDoc;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.RestauranteResponse;
import br.com.gestrest.api.adapter.in.web.mapper.RestauranteWebMapper;
import br.com.gestrest.api.domain.model.ports.in.restaurante.AtualizarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.in.restaurante.BuscarRestaurantePorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.restaurante.CriarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.in.restaurante.ExcluirRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.in.restaurante.ListarRestauranteUseCase;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/restaurantes")
@RequiredArgsConstructor
public class RestauranteController implements RestauranteControllerDoc {

    private final CriarRestauranteUseCase criar;
    private final AtualizarRestauranteUseCase atualizar;
    private final BuscarRestaurantePorIdUseCase buscar;
    private final ListarRestauranteUseCase listar;
    private final ExcluirRestauranteUseCase excluir;
    private final RestauranteWebMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<RestauranteResponse> criar(
            @Valid @RequestBody CriarRestauranteRequest request) {

        var criado = criar.criar(mapper.toDomain(request));

        return ResponseEntity
                .created(URI.create("/api/v1/restaurantes/" + criado.getId()))
                .body(mapper.toResponse(criado));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(
                mapper.toResponse(buscar.executar(id))
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<List<RestauranteResponse>> listar() {
        return ResponseEntity.ok(
                listar.executar().stream()
                        .map(mapper::toResponse)
                        .toList()
        );
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarRestauranteRequest request) {

        var atualizado = atualizar.atualizar(
                mapper.toDomain(id, request)
        );

        return ResponseEntity.ok(mapper.toResponse(atualizado));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        excluir.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
