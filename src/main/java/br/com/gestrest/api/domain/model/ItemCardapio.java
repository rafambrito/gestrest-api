package br.com.gestrest.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ItemCardapio {

	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private Long restauranteId;
	private LocalDateTime dataUltimaAlteracao;

	private ItemCardapio(Long id, String nome, String descricao, BigDecimal preco, Long restauranteId) {

		validar(nome, preco, restauranteId);

		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.restauranteId = restauranteId;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	public static ItemCardapio criar(String nome, String descricao, BigDecimal preco, Long restauranteId) {
		return new ItemCardapio(null, nome, descricao, preco, restauranteId);
	}

	public static ItemCardapio existente(Long id, String nome, String descricao, BigDecimal preco, Long restauranteId) {
		return new ItemCardapio(id, nome, descricao, preco, restauranteId);
	}

	public void atualizar(String nome, String descricao, BigDecimal preco) {
		validar(nome, preco, restauranteId);
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	private void validar(String nome, BigDecimal preco, Long restauranteId) {

		if (nome == null || nome.isBlank())
			throw new IllegalArgumentException("Nome é obrigatório");

		if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Preço deve ser maior que zero");

		if (restauranteId == null)
			throw new IllegalArgumentException("Restaurante é obrigatório");
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public Long getRestauranteId() {
		return restauranteId;
	}

	public LocalDateTime getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ItemCardapio that))
			return false;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
