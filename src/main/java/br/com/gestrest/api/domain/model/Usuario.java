package br.com.gestrest.api.domain.model;

import java.time.LocalDateTime;

public class Usuario {

	private Long id;
	private String nome;
	private String email;
	private String login;
	private String senha;
	private String endereco;

	private TipoUsuario tipoUsuario;
	
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAlteracao;

	private Usuario(Long id, String nome, String email, String login, String senha, String endereco,
			TipoUsuario tipoUsuario) {

		this.id = id;
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.endereco = endereco;
		this.tipoUsuario = tipoUsuario;
	}

	public static Usuario criar(String nome, String email, String login, String senha, String endereco,
			TipoUsuario tipoUsuario) {

		return new Usuario(null, nome, email, login, senha, endereco, tipoUsuario);
	}

	public static Usuario existente(Long id, String nome, String email, String login, String senha, String endereco,
			TipoUsuario tipoUsuario) {

		return new Usuario(id, nome, email, login, senha, endereco, tipoUsuario);
	}
    
	public void alterarTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
    public void atualizarDados(String nome) {
        this.nome = nome;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
