package br.com.gestrest.api.domain.model;

import java.util.Objects;

public class TipoUsuario {
    private static final int NOME_MAX_LENGTH = 50;

	private Long id;
    private String nome;

    private TipoUsuario(Long id, String nome) {
        validarNome(nome);
        this.id = id;
        this.nome = nome;
    }

    public static TipoUsuario criar(String nome) {
        return new TipoUsuario(null, nome);
    }

    public static TipoUsuario existente(Long id, String nome) {
        if (id == null) {
            throw new IllegalArgumentException("Id não pode ser nulo para objeto existente");
        }
        return new TipoUsuario(id, nome);
    }

    public void atualizarDados(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do tipo de usuário é obrigatório");
        }

		if (nome.length() > NOME_MAX_LENGTH) {
			throw new IllegalArgumentException("Nome deve ter no máximo 50 caracteres");
		}
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoUsuario that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}