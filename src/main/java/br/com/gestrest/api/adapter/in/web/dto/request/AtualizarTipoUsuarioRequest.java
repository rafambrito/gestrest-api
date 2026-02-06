package br.com.gestrest.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AtualizarTipoUsuarioRequest(

		@NotBlank(message = "Nome é obrigatório") String nome) {
}