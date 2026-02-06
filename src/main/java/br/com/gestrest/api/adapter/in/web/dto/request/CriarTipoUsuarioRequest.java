package br.com.gestrest.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CriarTipoUsuarioRequest(

		@NotBlank(message = "Nome é obrigatório") String nome,

		String descricao

) {
}