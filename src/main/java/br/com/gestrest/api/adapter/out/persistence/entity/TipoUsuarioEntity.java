package br.com.gestrest.api.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tipo_usuario")
public class TipoUsuarioEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_usuario_id")
    private Long id;

    @Column(name = "nome_tipo", nullable = false, length = 45)
    private String nome;
}