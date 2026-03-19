package br.com.gestrest.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizarRestauranteRequest(
    @NotBlank @Size(max = 150) String nome, 
    @NotBlank @Size(max = 250) String endereco,
    @NotBlank @Size(max = 100) String tipoCozinha,
    @NotBlank @Size(max = 100) String horarioFuncionamento,
    @NotNull(message = "Dono é obrigatório") Long donoId) {
}