package br.com.gestrest.api.application.usecase.command.usuario;

public record CriarUsuarioCommand(
        String nome,
        String email,
        String login,
        String senha,
        String endereco,
        Long tipoUsuarioId
) {}