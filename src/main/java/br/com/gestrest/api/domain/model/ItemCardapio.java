package br.com.gestrest.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ItemCardapio {

	private static final int NOME_MAX_LENGTH = 150;
	private static final int DESCRICAO_MAX_LENGTH = 500;
	private static final int FOTO_PATH_MAX_LENGTH = 255;
	private static final int PRECO_MAX_INTEGER_DIGITS = 10;
	private static final int PRECO_MAX_FRACTION_DIGITS = 2;

	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private Long restauranteId;
	private boolean disponivelSomenteNoLocal;
	private String fotoPath;
	private LocalDateTime dataUltimaAlteracao;

	private ItemCardapio(Long id, String nome, String descricao, BigDecimal preco, Long restauranteId,
			boolean disponivelSomenteNoLocal, String fotoPath) {

		validar(nome, descricao, preco, restauranteId, fotoPath);

		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.restauranteId = restauranteId;
		this.disponivelSomenteNoLocal = disponivelSomenteNoLocal;
		this.fotoPath = fotoPath;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	public static ItemCardapio criar(String nome, String descricao, BigDecimal preco, Long restauranteId,
			boolean disponivelSomenteNoLocal, String fotoPath) {
		return new ItemCardapio(null, nome, descricao, preco, restauranteId, disponivelSomenteNoLocal, fotoPath);
	}

	public static ItemCardapio existente(Long id, String nome, String descricao, BigDecimal preco, Long restauranteId,
			boolean disponivelSomenteNoLocal, String fotoPath) {
		return new ItemCardapio(id, nome, descricao, preco, restauranteId, disponivelSomenteNoLocal, fotoPath);
	}

	public void atualizar(String nome, String descricao, BigDecimal preco, boolean disponivelSomenteNoLocal,
			String fotoPath) {
		validar(nome, descricao, preco, restauranteId, fotoPath);
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.disponivelSomenteNoLocal = disponivelSomenteNoLocal;
		this.fotoPath = fotoPath;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	private void validar(String nome, String descricao, BigDecimal preco, Long restauranteId, String fotoPath) {

		if (nome == null || nome.isBlank())
			throw new IllegalArgumentException("Nome é obrigatório");

		if (nome.length() > NOME_MAX_LENGTH)
			throw new IllegalArgumentException("Nome deve ter no máximo 150 caracteres");

		if (descricao == null || descricao.isBlank())
			throw new IllegalArgumentException("Descrição é obrigatória");

		if (descricao.length() > DESCRICAO_MAX_LENGTH)
			throw new IllegalArgumentException("Descrição deve ter no máximo 500 caracteres");

		if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Preço deve ser maior que zero");

		int integerDigits = Math.max(preco.precision() - preco.scale(), 0);
		int fractionDigits = Math.max(preco.scale(), 0);
		if (integerDigits > PRECO_MAX_INTEGER_DIGITS || fractionDigits > PRECO_MAX_FRACTION_DIGITS) {
			throw new IllegalArgumentException("Preço deve ter no máximo 10 dígitos inteiros e 2 decimais");
		}

		if (restauranteId == null)
			throw new IllegalArgumentException("Restaurante é obrigatório");

		if (fotoPath == null || fotoPath.isBlank())
			throw new IllegalArgumentException("Caminho da foto é obrigatório");

		if (fotoPath.length() > FOTO_PATH_MAX_LENGTH)
			throw new IllegalArgumentException("Caminho da foto deve ter no máximo 255 caracteres");
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

	public boolean isDisponivelSomenteNoLocal() {
		return disponivelSomenteNoLocal;
	}

	public String getFotoPath() {
		return fotoPath;
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
