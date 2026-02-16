package br.com.gestrest.api.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Restaurante {

	private Long id;
	private String nome;
	private String endereco;
	private Long donoId;
	private LocalDateTime dataUltimaAlteracao;

	private Restaurante(Long id, String nome, String endereco, Long donoId) {
		validar(nome, endereco, donoId);
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.donoId = donoId;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	public static Restaurante criar(String nome, String endereco, Long donoId) {
		return new Restaurante(null, nome, endereco, donoId);
	}

	public static Restaurante existente(Long id, String nome, String endereco, Long donoId) {
		return new Restaurante(id, nome, endereco, donoId);
	}

	public void atualizar(String nome, String endereco) {
		validar(nome, endereco, this.donoId);
		this.nome = nome;
		this.endereco = endereco;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	private void validar(String nome, String endereco, Long donoId) {
		if (nome == null || nome.isBlank())
			throw new IllegalArgumentException("Nome é obrigatório");

		if (endereco == null || endereco.isBlank())
			throw new IllegalArgumentException("Endereço é obrigatório");

		if (donoId == null)
			throw new IllegalArgumentException("Dono é obrigatório");
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public Long getDonoId() {
		return donoId;
	}

	public LocalDateTime getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Restaurante that))
			return false;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
