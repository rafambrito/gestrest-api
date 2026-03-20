package br.com.gestrest.api.domain.model;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Usuario {

	private static final int NOME_MAX_LENGTH = 100;
	private static final int EMAIL_MAX_LENGTH = 150;
	private static final int LOGIN_MAX_LENGTH = 50;
	private static final int SENHA_MIN_LENGTH = 6;
	private static final Pattern EMAIL_PATTERN =
			Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

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
				TipoUsuario tipoUsuario, LocalDateTime dataCriacao, LocalDateTime dataUltimaAlteracao) {

		validar(nome, email, login, senha, tipoUsuario);

		this.id = id;
		this.nome = nome;
		this.email = email;
		this.login = login;
		this.senha = senha;
		this.endereco = endereco;
		this.tipoUsuario = tipoUsuario;
		this.dataCriacao = dataCriacao;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public static Usuario criar(String nome, String email, String login, String senha, String endereco,
				 TipoUsuario tipoUsuario) {

		return new Usuario(null, nome, email, login, senha, endereco, tipoUsuario, LocalDateTime.now(), null);
	}

	public static Usuario existente(Long id, String nome, String email, String login, String senha, String endereco,
			 TipoUsuario tipoUsuario) {

		return new Usuario(id, nome, email, login, senha, endereco, tipoUsuario, null, null);
	}

	public static Usuario existente(Long id, String nome, String email, String login, String senha, String endereco,
			 TipoUsuario tipoUsuario, LocalDateTime dataCriacao, LocalDateTime dataUltimaAlteracao) {

		return new Usuario(id, nome, email, login, senha, endereco, tipoUsuario, dataCriacao, dataUltimaAlteracao);
	}
    
	public void alterarTipoUsuario(TipoUsuario tipoUsuario) {
		validarTipoUsuario(tipoUsuario);
		this.tipoUsuario = tipoUsuario;
	}
	
    public void atualizarDados(String nome) {
		validarNome(nome);
        this.nome = nome;
        this.dataUltimaAlteracao = LocalDateTime.now();
    }

    public void atualizar(String nome, String email, String endereco, TipoUsuario tipoUsuario) {
    	validarNome(nome);
    	validarEmail(email);
    	validarTipoUsuario(tipoUsuario);

        this.nome = nome;
        this.email = email;
        this.endereco = (endereco != null) ? endereco : this.endereco;
        this.tipoUsuario = tipoUsuario;

        this.dataUltimaAlteracao = LocalDateTime.now();
    }

	private void validar(String nome,
						 String email,
						 String login,
						 String senha,
						 TipoUsuario tipoUsuario) {

		validarNome(nome);
		validarEmail(email);
		validarLogin(login);
		validarSenha(senha);
		validarTipoUsuario(tipoUsuario);
	}

	private void validarNome(String nome) {
		if (nome == null || nome.isBlank()) {
			throw new IllegalArgumentException("Nome é obrigatório");
		}

		if (nome.length() > NOME_MAX_LENGTH) {
			throw new IllegalArgumentException("Nome deve ter no máximo 100 caracteres");
		}
	}

	private void validarEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Email é obrigatório");
		}

		if (email.length() > EMAIL_MAX_LENGTH) {
			throw new IllegalArgumentException("Email deve ter no máximo 150 caracteres");
		}

		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new IllegalArgumentException("Email deve ser válido");
		}
	}

	private void validarLogin(String login) {
		if (login == null || login.isBlank()) {
			throw new IllegalArgumentException("Login é obrigatório");
		}

		if (login.length() > LOGIN_MAX_LENGTH) {
			throw new IllegalArgumentException("Login deve ter no máximo 50 caracteres");
		}
	}

	private void validarSenha(String senha) {
		if (senha == null || senha.isBlank()) {
			throw new IllegalArgumentException("Senha é obrigatória");
		}

		if (senha.length() < SENHA_MIN_LENGTH) {
			throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
		}
	}

	private void validarTipoUsuario(TipoUsuario tipoUsuario) {
		if (tipoUsuario == null) {
			throw new IllegalArgumentException("Tipo de usuário é obrigatório");
		}
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

	public String getEmail() {
		return email;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public String getEndereco() {
		return endereco;
	}

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

	public boolean isDono() {
		return this.tipoUsuario != null
				&& TipoUsuarioEnum.DONO_RESTAURANTE.getId().equals(this.tipoUsuario.getId());
	}

	public boolean isCliente() {
		return this.tipoUsuario != null
				&& TipoUsuarioEnum.CLIENTE.getId().equals(this.tipoUsuario.getId());
	}
}