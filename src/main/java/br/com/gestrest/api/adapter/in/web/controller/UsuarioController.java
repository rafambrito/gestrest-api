package br.com.gestrest.api.adapter.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarUsuarioRequest;
import br.com.gestrest.api.application.usecase.usuario.command.CriarUsuarioCommand;
import br.com.gestrest.api.domain.model.ports.in.CriarUsuarioUseCase;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final CriarUsuarioUseCase criarUseCase;
	
	@PostMapping("/usuarios")
	public ResponseEntity<?> criar(@RequestBody CriarUsuarioRequest request) {

	    var command = new CriarUsuarioCommand(
	            request.nome(),
	            request.email(),
	            request.login(),
	            request.senha(),
	            request.endereco(),
	            request.tipoUsuarioId()
	    );

	    var usuario = criarUseCase.criar(command);

	    return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}
}
