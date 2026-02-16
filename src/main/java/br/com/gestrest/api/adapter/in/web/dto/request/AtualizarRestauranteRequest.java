package br.com.gestrest.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AtualizarRestauranteRequest(@NotBlank String nome, @NotBlank String endereco) {
}
