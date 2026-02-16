package br.com.gestrest.api.adapter.in.web.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizarItemCardapioRequest(@NotBlank String nome, String descricao,
		@NotNull @Positive BigDecimal preco) {
}
