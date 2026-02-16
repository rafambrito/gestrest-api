package br.com.gestrest.api.domain.model.ports.in.restaurante;

import br.com.gestrest.api.domain.model.Restaurante;

public interface CriarRestauranteUseCase {
	 Restaurante criar(Restaurante restaurante);
}
