package br.com.gestrest.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarUsuarioRequest(
        @NotBlank(message = "Nome é obrigatório") @Size(max = 100) String nome,

        @NotBlank(message = "Email é obrigatório") @Email(message = "Email deve ser válido") @Size(max = 150) String email,

        @NotBlank(message = "Login é obrigatório") @Size(max = 50) String login,

        @NotBlank(message = "Senha é obrigatória") @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres") String senha,

        String endereco,

        @NotNull(message = "Tipo de usuário é obrigatório") Long tipoUsuarioId) {
}