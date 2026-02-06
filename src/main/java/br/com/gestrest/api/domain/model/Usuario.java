package br.com.gestrest.api.domain.model;

import java.time.LocalDateTime;

public class Usuario {
	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private String endereco;
	private LocalDateTime dataUltimaAlteracao;
	private TipoUsuario tipoUsuario;

	public Usuario(Long id, String nome, String email, String login, String senha, String endereco,
			TipoUsuario tipoUsuario) {

		this.id = id;
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.endereco = endereco;
		this.tipoUsuario = tipoUsuario;
		this.dataUltimaAlteracao = LocalDateTime.now();
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public LocalDateTime getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
