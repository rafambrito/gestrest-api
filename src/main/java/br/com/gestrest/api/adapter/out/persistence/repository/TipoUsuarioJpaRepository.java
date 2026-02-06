package br.com.gestrest.api.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gestrest.api.adapter.out.persistence.entity.TipoUsuarioEntity;

@Repository
public interface TipoUsuarioJpaRepository extends JpaRepository<TipoUsuarioEntity, Long> {
}