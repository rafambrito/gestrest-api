package br.com.gestrest.api.domain.model.ports.out;

import java.util.List;
import java.util.Optional;

import br.com.gestrest.api.domain.model.Restaurante;

public interface RestauranteRepositoryPort {
	Restaurante salvar(Restaurante restaurante);

	Optional<Restaurante> buscarPorId(Long id);

	List<Restaurante> listar();

	void deletar(Long id);
}
