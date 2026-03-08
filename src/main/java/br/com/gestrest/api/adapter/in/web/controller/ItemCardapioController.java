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

import br.com.gestrest.api.adapter.in.web.controller.doc.ItemCardapioControllerDoc;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarItemCardapioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.ItemCardapioResponse;
import br.com.gestrest.api.adapter.in.web.mapper.ItemCardapioWebMapper;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.AtualizarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.BuscarItemCardapioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.CriarItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.ExcluirItemCardapioUseCase;
import br.com.gestrest.api.domain.model.ports.in.itemcardapio.ListarItensPorRestauranteUseCase;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/itens-cardapio")
@RequiredArgsConstructor
public class ItemCardapioController implements ItemCardapioControllerDoc {

    private final CriarItemCardapioUseCase criar;
    private final AtualizarItemCardapioUseCase atualizar;
    private final BuscarItemCardapioPorIdUseCase buscar;
    private final ListarItensPorRestauranteUseCase listar;
    private final ExcluirItemCardapioUseCase excluir;
    private final ItemCardapioWebMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<ItemCardapioResponse> criar(
            @Valid @RequestBody CriarItemCardapioRequest request) {

        var criado = criar.criar(mapper.toDomain(request));

        return ResponseEntity
                .created(URI.create("/api/v1/itens-cardapio/" + criado.getId()))
                .body(mapper.toResponse(criado));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ItemCardapioResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(buscar.buscarPorId(id)));
    }

    @Override
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ItemCardapioResponse>> listarPorRestaurante(
            @PathVariable Long restauranteId) {

        return ResponseEntity.ok(
                listar.listarPorRestauranteId(restauranteId)
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ItemCardapioResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarItemCardapioRequest request) {

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
