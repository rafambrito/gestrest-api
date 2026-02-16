package br.com.gestrest.api.adapter.in.web.dto.response;

public record RestauranteResponse(Long id, String nome, String endereco, String tipoCozinha, String horarioFuncionamento, Long donoId) {
}
