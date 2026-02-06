package br.com.gestrest.api.domain.model.ports.out;

import java.util.List;
import java.util.Optional;

import br.com.gestrest.api.domain.model.TipoUsuario;

public interface TipoUsuarioRepositoryPort {
    TipoUsuario salvar(TipoUsuario tipoUsuario);

    List<TipoUsuario> listar();

    Optional<TipoUsuario> buscarPorId(Long id);

    void deletar(Long id);
}
