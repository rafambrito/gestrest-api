package br.com.gestrest.api.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gestrest.api.adapter.out.persistence.entity.RestauranteEntity;

public interface RestauranteJpaRepository extends JpaRepository<RestauranteEntity, Long> {
	
}
