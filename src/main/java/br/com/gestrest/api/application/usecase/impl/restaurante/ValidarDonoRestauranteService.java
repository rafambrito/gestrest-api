package br.com.gestrest.api.application.usecase.impl.restaurante;

import org.springframework.stereotype.Service;

import br.com.gestrest.api.domain.exception.BusinessException;
import br.com.gestrest.api.domain.exception.UsuarioNaoEncontradoException;
import br.com.gestrest.api.domain.model.ports.out.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidarDonoRestauranteService {

    private final UsuarioRepositoryPort usuarioRepository;

    public void validar(Long donoId) {
        var usuario = usuarioRepository.buscarPorId(donoId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(donoId));

        if (!usuario.isDono()) {
            throw new BusinessException("O dono informado deve ser um usuário do tipo dono de restaurante");
        }
    }
}
