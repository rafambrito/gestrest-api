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
import org.springframework.web.bind.annotation.RequestBody;

import br.com.gestrest.api.adapter.in.web.controller.doc.UsuarioControllerDoc;
import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.UsuarioResponse;
import br.com.gestrest.api.adapter.in.web.mapper.UsuarioWebMapper;
import br.com.gestrest.api.domain.model.ports.in.usuario.AtualizarUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.usuario.BuscarUsuarioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.usuario.CriarUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.usuario.ExcluirUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.usuario.ListarUsuariosUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioControllerDoc {

	private final CriarUsuarioUseCase criarUseCase;
	private final AtualizarUsuarioUseCase atualizarUseCase;
	private final BuscarUsuarioPorIdUseCase buscarPorIdUseCase;
	private final ListarUsuariosUseCase listarUseCase;
	private final ExcluirUsuarioUseCase excluirUseCase;
	private final UsuarioWebMapper mapper;

	@Override
	@PostMapping
	public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody CriarUsuarioRequest request) {

		var command = mapper.toDomain(request);
		var usuario = criarUseCase.criar(command);
		var response = mapper.toResponse(usuario);

		return ResponseEntity.created(URI.create("/api/v1/usuarios/" + response.id())).body(response);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id,
			@Valid @RequestBody AtualizarUsuarioRequest request) {

		var usuario = atualizarUseCase.atualizar(mapper.toDomain(id, request));
		var response = mapper.toResponse(usuario);

		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponse> buscar(@PathVariable Long id) {

		var usuario = buscarPorIdUseCase.executar(id)
			.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		return ResponseEntity.ok(mapper.toResponse(usuario));
	}

	@Override
	@GetMapping
	public ResponseEntity<List<UsuarioResponse>> listar() {

		var lista = listarUseCase.executar().stream().map(mapper::toResponse).toList();

		return ResponseEntity.ok(lista);
	}

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {

		excluirUseCase.executar(id);

		return ResponseEntity.noContent().build();
	}
}