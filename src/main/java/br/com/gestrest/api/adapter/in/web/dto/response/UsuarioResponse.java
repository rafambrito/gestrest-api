package br.com.gestrest.api.adapter.in.web.dto.response;

import java.time.LocalDateTime;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    String login,
    String endereco,
    TipoUsuarioResponse tipoUsuario,
    LocalDateTime dataCriacao,
    LocalDateTime dataUltimaAlteracao
) {}
