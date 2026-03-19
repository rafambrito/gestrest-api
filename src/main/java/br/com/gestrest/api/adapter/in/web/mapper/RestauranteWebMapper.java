package br.com.gestrest.api.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import br.com.gestrest.api.adapter.in.web.dto.request.AtualizarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.request.CriarRestauranteRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.RestauranteResponse;
import br.com.gestrest.api.domain.model.Restaurante;

@Component
public class RestauranteWebMapper {

    public Restaurante toDomain(CriarRestauranteRequest request) {
        return Restaurante.criar(
                request.nome(),
                request.endereco(),
                request.tipoCozinha(),
                request.horarioFuncionamento(),
                request.donoId()
        );
    }

    public Restaurante toDomain(Long id, AtualizarRestauranteRequest request) {
        return Restaurante.existente(
                id,
                request.nome(),
                request.endereco(),
                request.tipoCozinha(),
                request.horarioFuncionamento(),
                request.donoId()
        );
    }

    public RestauranteResponse toResponse(Restaurante domain) {
        return new RestauranteResponse(
                domain.getId(),
                domain.getNome(),
                domain.getEndereco(),
                domain.getTipoCozinha(),
                domain.getHorarioFuncionamento(),
                domain.getDonoId()
        );
    }
}
