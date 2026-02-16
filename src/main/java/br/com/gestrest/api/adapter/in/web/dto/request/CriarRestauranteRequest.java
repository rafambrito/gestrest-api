package br.com.gestrest.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarRestauranteRequest(@NotBlank String nome, @NotBlank String endereco, @NotNull Long donoId) {
}
