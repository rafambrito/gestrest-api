package br.com.gestrest.api.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarTipoUsuarioRequest(

        @NotBlank(message = "Nome é obrigatório") @Size(max = 50) String nome,

        @Size(max = 250) String descricao

) {
}