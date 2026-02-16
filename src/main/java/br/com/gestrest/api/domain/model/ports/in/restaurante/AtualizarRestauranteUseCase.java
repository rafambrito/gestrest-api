package br.com.gestrest.api.domain.model.ports.in.restaurante;

import br.com.gestrest.api.domain.model.Restaurante;

public interface AtualizarRestauranteUseCase {
	Restaurante atualizar(Restaurante restaurante);
}
