package br.com.gestrest.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.domain.exception.TipoUsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.exception.RecursoEmUsoException;
import br.com.gestrest.api.adapter.out.persistence.entity.TipoUsuarioEntity;
import br.com.gestrest.api.adapter.out.persistence.mapper.TipoUsuarioPersistenceMapper;
import br.com.gestrest.api.adapter.out.persistence.repository.TipoUsuarioJpaRepository;
import br.com.gestrest.api.adapter.out.persistence.repository.UsuarioJpaRepository;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.out.TipoUsuarioRepositoryPort;

@Component
public class TipoUsuarioRepositoryAdapter implements TipoUsuarioRepositoryPort {

    private final TipoUsuarioJpaRepository repository;
    private final TipoUsuarioPersistenceMapper mapper;
    private final UsuarioJpaRepository usuarioRepository;

    public TipoUsuarioRepositoryAdapter(
            TipoUsuarioJpaRepository repository,
            TipoUsuarioPersistenceMapper mapper,
            UsuarioJpaRepository usuarioRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public TipoUsuario salvar(TipoUsuario tipo) {

        TipoUsuarioEntity entity;

        if (tipo.getId() != null) {

            entity = repository.findById(tipo.getId())
                    .orElseThrow(() ->
                            new TipoUsuarioNaoEncontradoException(tipo.getId()));

        } else {
            entity = new TipoUsuarioEntity();
        }

        entity.setNome(tipo.getNome());

        entity = repository.save(entity);

        return mapper.toDomain(entity);
    }

	@Override
	public List<TipoUsuario> listar() {
	    return repository.findAll()
	            .stream()
	            .map(mapper::toDomain)
	            .toList();
	}

	@Override
	public Optional<TipoUsuario> buscarPorId(Long id) {
	    return repository.findById(id)
	            .map(mapper::toDomain);
	}

	@Override
	public void deletar(Long id) {
	    if (!repository.existsById(id)) {
	        return;
	    }

        if (usuarioRepository.existsByTipoUsuarioId(id)) {
            throw new RecursoEmUsoException("O tipo de usuário possui usuários associados e não pode ser excluído");
        }

	    repository.deleteById(id);
	}
}