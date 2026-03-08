package br.com.gestrest.api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.out.persistence.entity.UsuarioEntity;
import br.com.gestrest.api.adapter.out.persistence.mapper.UsuarioPersistenceMapper;
import br.com.gestrest.api.adapter.out.persistence.repository.TipoUsuarioJpaRepository;
import br.com.gestrest.api.adapter.out.persistence.repository.UsuarioJpaRepository;
import br.com.gestrest.api.domain.model.Usuario;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository repository;
    private final TipoUsuarioJpaRepository tipoRepository;
    private final UsuarioPersistenceMapper mapper;

    @Override
    public Usuario salvar(Usuario usuario) {

        UsuarioEntity entity;

        if (usuario.getId() != null) {
            entity = repository.findById(usuario.getId()).orElse(new UsuarioEntity());
        } else {
            entity = new UsuarioEntity();
        }

        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setLogin(usuario.getLogin());
        entity.setSenha(usuario.getSenha());
        entity.setEndereco(usuario.getEndereco());

        var tipoEntity = tipoRepository.findById(
                usuario.getTipoUsuario().getId()
        ).orElseThrow();

        entity.setTipoUsuario(tipoEntity);

        entity = repository.save(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorLogin(String login) {
        return repository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> listar() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}