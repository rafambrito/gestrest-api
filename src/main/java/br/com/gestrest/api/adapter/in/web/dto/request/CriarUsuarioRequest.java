package br.com.gestrest.api.adapter.in.web.dto.request;

public record CriarUsuarioRequest(
        String nome,
        String email,
        String login,
        String senha,
        String endereco,
        Long tipoUsuarioId
) {}