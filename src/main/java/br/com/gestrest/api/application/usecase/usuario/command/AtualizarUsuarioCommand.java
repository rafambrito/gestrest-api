package br.com.gestrest.api.application.usecase.usuario.command;

public record AtualizarUsuarioCommand(
    Long id,
    String nome,
    String email,
    String endereco,
    Long tipoUsuarioId
) {}
