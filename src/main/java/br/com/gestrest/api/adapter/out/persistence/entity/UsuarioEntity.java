package br.com.gestrest.api.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String login;
    private String senha;
    private String endereco;

    private LocalDateTime dataUltimaAlteracao;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id")
    private TipoUsuarioEntity tipoUsuario;
}
