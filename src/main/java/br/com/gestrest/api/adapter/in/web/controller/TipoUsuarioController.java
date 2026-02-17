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

import br.com.gestrest.api.adapter.in.web.controller.doc.TipoUsuarioControllerDoc;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarTipoUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarTipoUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.TipoUsuarioResponse;
import br.com.gestrest.api.adapter.in.web.mapper.TipoUsuarioWebMapper;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.AtualizarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.CriarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.ExcluirTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.ListarTipoUsuarioUseCase;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tipos-usuarios")
@RequiredArgsConstructor
public class TipoUsuarioController implements TipoUsuarioControllerDoc {

	private final CriarTipoUsuarioUseCase criarUseCase;
	private final AtualizarTipoUsuarioUseCase atualizarUseCase;
	private final BuscarTipoUsuarioPorIdUseCase buscarPorIdUseCase;
	private final ListarTipoUsuarioUseCase listarUseCase;
	private final ExcluirTipoUsuarioUseCase excluirUseCase;
	private final TipoUsuarioWebMapper mapper;

	@Override
	@PostMapping
	public ResponseEntity<TipoUsuarioResponse> criar(@Valid @RequestBody CriarTipoUsuarioRequest request) {

		var domain = mapper.toDomain(request);
		var criado = criarUseCase.criar(domain);

		var response = mapper.toResponse(criado);

		return ResponseEntity.created(URI.create("/api/v1/tipos-usuario/" + response.id())).body(response);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<TipoUsuarioResponse> atualizar(@PathVariable Long id,
			@Valid @RequestBody AtualizarTipoUsuarioRequest request) {

		var domain = mapper.toDomain(id, request);
		var atualizado = atualizarUseCase.atualizar(domain);

		return ResponseEntity.ok(mapper.toResponse(atualizado));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<TipoUsuarioResponse> buscar(@PathVariable Long id) {

		var tipoUsuario = buscarPorIdUseCase.buscarPorId(id);

		return ResponseEntity.ok(mapper.toResponse(tipoUsuario));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<TipoUsuarioResponse>> listar() {

		var lista = listarUseCase.listar().stream().map(mapper::toResponse).toList();

		return ResponseEntity.ok(lista);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {

		excluirUseCase.deletar(id);

		return ResponseEntity.noContent().build();
	}
}