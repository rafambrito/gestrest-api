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

        String nome = request.nome() != null ? request.nome().trim() : null;
        String email = request.email() != null ? request.email().trim().toLowerCase() : null;
        String login = request.login() != null ? request.login().trim().toLowerCase() : null;
        String senha = request.senha();
        String endereco = request.endereco() != null ? request.endereco().trim() : null;

        return new CriarUsuarioCommand(
            nome,
            email,
            login,
            senha,
            endereco,
            request.tipoUsuarioId()
        );
    }

    public AtualizarUsuarioCommand toDomain(Long id, AtualizarUsuarioRequest request) {
        if (request == null) {
            return null;
        }

        String nome = request.nome() != null ? request.nome().trim() : null;
        String email = request.email() != null ? request.email().trim().toLowerCase() : null;
        String endereco = request.endereco() != null ? request.endereco().trim() : null;

        return new AtualizarUsuarioCommand(
            id,
            nome,
            email,
            endereco,
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
            tipoResponse,
            domain.getDataCriacao(),
            domain.getDataUltimaAlteracao()
        );
    }
}