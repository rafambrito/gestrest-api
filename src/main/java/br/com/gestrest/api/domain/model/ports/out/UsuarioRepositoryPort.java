package br.com.gestrest.api.domain.model.ports.out;

import java.util.List;
import java.util.Optional;

import br.com.gestrest.api.domain.model.Usuario;

public interface UsuarioRepositoryPort {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorId(Long id);

    List<Usuario> listar();

    void deletar(Long id);
}
