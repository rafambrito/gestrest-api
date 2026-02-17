package br.com.gestrest.api.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Restaurante {

	private Long id;
	private String nome;
	private String endereco;
	private String tipoCozinha;
	private String horarioFuncionamento;
	private Long donoId;
	private LocalDateTime dataUltimaAlteracao;

	private Restaurante(Long id, String nome, String endereco, String tipoCozinha, String horarioFuncionamento, Long donoId) {
		validar(nome, endereco, tipoCozinha, horarioFuncionamento, donoId);
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;
		this.tipoCozinha = tipoCozinha;
		this.horarioFuncionamento = horarioFuncionamento;
		this.donoId = donoId;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	public static Restaurante criar(String nome, String endereco, String tipoCozinha, String horarioFuncionamento, Long donoId) {
		return new Restaurante(null, nome, endereco, tipoCozinha, horarioFuncionamento, donoId);
	}

	public static Restaurante existente(Long id, String nome, String endereco, String tipoCozinha, String horarioFuncionamento, Long donoId) {
		return new Restaurante(id, nome, endereco, tipoCozinha, horarioFuncionamento, donoId);
	}

	public void atualizar(String nome, String endereco, String tipoCozinha, String horarioFuncionamento) {
		validar(nome, endereco, tipoCozinha, horarioFuncionamento, this.donoId);
		this.nome = nome;
		this.endereco = endereco;
		this.tipoCozinha = tipoCozinha;
		this.horarioFuncionamento = horarioFuncionamento;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	private void validar(String nome, String endereco, String tipoCozinha, String horarioFuncionamento, Long donoId) {
		if (nome == null || nome.isBlank())
			throw new IllegalArgumentException("Nome é obrigatório");

		if (endereco == null || endereco.isBlank())
			throw new IllegalArgumentException("Endereço é obrigatório");

		if (tipoCozinha == null || tipoCozinha.isBlank())
			throw new IllegalArgumentException("Tipo de cozinha é obrigatório");

		if (horarioFuncionamento == null || horarioFuncionamento.isBlank())
			throw new IllegalArgumentException("Horário de funcionamento é obrigatório");

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

	public String getTipoCozinha() {
		return tipoCozinha;
	}

	public String getHorarioFuncionamento() {
		return horarioFuncionamento;
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
