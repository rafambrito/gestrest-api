package br.com.gestrest.api.adapter.in.web.dto.response;

import br.com.gestrest.api.adapter.in.web.dto.response.TipoUsuarioResponse;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    String login,
    String endereco,
    TipoUsuarioResponse tipoUsuario
) {}
