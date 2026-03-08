package br.com.gestrest.api.adapter.in.web.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CriarItemCardapioRequest(
        @NotBlank @Size(max = 150) String nome,
        @NotBlank @Size(max = 500) String descricao,
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal preco,
        @NotNull Long restauranteId) {
}