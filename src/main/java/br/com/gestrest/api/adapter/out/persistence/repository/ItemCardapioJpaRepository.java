package br.com.gestrest.api.adapter.out.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestrest.api.adapter.out.persistence.entity.ItemCardapioEntity;

public interface ItemCardapioJpaRepository extends JpaRepository<ItemCardapioEntity, Long> {

	List<ItemCardapioEntity> findByRestauranteId(Long restauranteId);
}
