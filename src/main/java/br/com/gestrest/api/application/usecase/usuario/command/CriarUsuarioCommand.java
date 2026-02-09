package br.com.gestrest.api.application.usecase.usuario.command;

public record CriarUsuarioCommand(
        String nome,
        String email,
        String login,
        String senha,
        String endereco,
        Long tipoUsuarioId
) {}