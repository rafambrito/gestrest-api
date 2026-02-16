package br.com.gestrest.api.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.TipoUsuarioResponse;
import br.com.gestrest.api.adapter.in.web.dto.response.UsuarioResponse;
import br.com.gestrest.api.application.usecase.command.usuario.AtualizarUsuarioCommand;
import br.com.gestrest.api.application.usecase.command.usuario.CriarUsuarioCommand;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.Usuario;

@Component
public class UsuarioWebMapper {

    public CriarUsuarioCommand toDomain(CriarUsuarioRequest request) {
        if (request == null) {
            return null;
        }
        
        return new CriarUsuarioCommand(
            request.nome(),
            request.email(),
            request.login(),
            request.senha(),
            request.endereco(),
            request.tipoUsuarioId()
        );
    }

    public AtualizarUsuarioCommand toDomain(Long id, AtualizarUsuarioRequest request) {
        if (request == null) {
            return null;
        }
        
        return new AtualizarUsuarioCommand(
            id,
            request.nome(),
            request.email(),
            request.endereco(),
            request.tipoUsuarioId()
        );
    }

    public UsuarioResponse toResponse(Usuario domain) {
        if (domain == null) {
            return null;
        }
        
        TipoUsuarioResponse tipoResponse = new TipoUsuarioResponse(
            domain.getTipoUsuario().getId(),
            domain.getTipoUsuario().getNome()
        );
        
        return new UsuarioResponse(
            domain.getId(),
            domain.getNome(),
            domain.getEmail(),
            domain.getLogin(),
            domain.getEndereco(),
            tipoResponse
        );
    }
}
