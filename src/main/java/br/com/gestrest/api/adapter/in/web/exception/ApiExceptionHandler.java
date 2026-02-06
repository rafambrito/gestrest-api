package br.com.gestrest.api.adapter.in.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntime(RuntimeException ex) {

		if (ex.getMessage().contains("não encontrado")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}
	
	@ExceptionHandler(TipoUsuarioNaoEncontradoException.class)
	public ResponseEntity<String> handleNotFound(TipoUsuarioNaoEncontradoException ex) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
}
