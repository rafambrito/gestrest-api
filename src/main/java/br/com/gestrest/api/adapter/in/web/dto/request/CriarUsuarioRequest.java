package br.com.gestrest.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CriarUsuarioRequest(@NotBlank(message = "Nome é obrigatório") String nome,

		@NotBlank(message = "Email é obrigatório") @Email(message = "Email deve ser válido") String email,

		@NotBlank(message = "Login é obrigatório") String login,

		@NotBlank(message = "Senha é obrigatória") String senha,

		String endereco,

		@NotNull(message = "Tipo de usuário é obrigatório") Long tipoUsuarioId) {
}