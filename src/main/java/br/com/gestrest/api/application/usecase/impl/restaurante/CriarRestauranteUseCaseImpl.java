package br.com.gestrest.api.application.usecase.impl.restaurante;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.adapter.in.web.exception.PermissaoNegadaException;
import br.com.gestrest.api.adapter.in.web.exception.UsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.Restaurante;
import br.com.gestrest.api.domain.model.ports.in.restaurante.CriarRestauranteUseCase;
import br.com.gestrest.api.domain.model.ports.out.RestauranteRepositoryPort;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriarRestauranteUseCaseImpl implements CriarRestauranteUseCase {

    private final RestauranteRepositoryPort repository;
    private final UsuarioRepositoryPort usuarioRepository;

    @Override
    public Restaurante criar(Restaurante restaurante) {
        var donoId = restaurante.getDonoId();
        var usuario = usuarioRepository.buscarPorId(donoId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(donoId));

        if (!usuario.isDono()) {
            throw new PermissaoNegadaException("Apenas donos de restaurante podem criar restaurantes");
        }

        return repository.salvar(restaurante);
    }
}