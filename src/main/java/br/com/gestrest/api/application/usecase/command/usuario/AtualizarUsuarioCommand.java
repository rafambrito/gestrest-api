package br.com.gestrest.api.application.usecase.command.usuario;

public record AtualizarUsuarioCommand(
    Long id,
    String nome,
    String email,
    String endereco,
    Long tipoUsuarioId
) {}
