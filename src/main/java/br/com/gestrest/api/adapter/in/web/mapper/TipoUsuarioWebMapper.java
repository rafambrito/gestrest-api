package br.com.gestrest.api.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarTipoUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarTipoUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.TipoUsuarioResponse;
import br.com.gestrest.api.domain.model.TipoUsuario;

@Component
public class TipoUsuarioWebMapper {

    public TipoUsuario toDomain(CriarTipoUsuarioRequest request) {
    	return TipoUsuario.criar(request.nome());
    }

    public TipoUsuario toDomain(Long id, AtualizarTipoUsuarioRequest request) {
       return TipoUsuario.existente(id, request.nome());
    }

    public TipoUsuarioResponse toResponse(TipoUsuario domain) {
        return new TipoUsuarioResponse(domain.getId(),domain.getNome());
    }
}
