package br.com.gestrest.api.adapter.in.web.dto.response;

import java.math.BigDecimal;

public record ItemCardapioResponse(Long id, String nome, String descricao, BigDecimal preco,
				Boolean disponivelSomenteNoLocal, String fotoPath, Long restauranteId) {
}
