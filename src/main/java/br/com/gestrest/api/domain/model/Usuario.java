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

    /**
     * Atualiza campos do usuário de forma transacional (única operação de domínio).
     * Preserve invariants and registra a nova data de alteração.
     */
    public void atualizar(String nome, String email, String endereco, TipoUsuario tipoUsuario) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }

        this.email = (email != null) ? email : this.email;
        this.endereco = (endereco != null) ? endereco : this.endereco;

        if (tipoUsuario != null) {
            this.tipoUsuario = tipoUsuario;
        }

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

	public boolean isDono() {
		return this.tipoUsuario != null && "DONO_RESTAURANTE".equals(this.tipoUsuario.getNome());
	}

	public boolean isCliente() {
		return this.tipoUsuario != null && "CLIENTE".equals(this.tipoUsuario.getNome());
	}
}