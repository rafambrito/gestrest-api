package br.com.gestrest.api.domain.model.ports.in.restaurante;

import java.util.List;

import br.com.gestrest.api.domain.model.Restaurante;

public interface ListarRestauranteUseCase {
	List<Restaurante> executar();
}
