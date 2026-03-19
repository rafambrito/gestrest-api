package br.com.gestrest.api.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gestrest.api.adapter.in.web.dto.request.CriarTipoUsuarioRequest;
import br.com.gestrest.api.adapter.in.web.dto.response.TipoUsuarioResponse;
import br.com.gestrest.api.adapter.in.web.mapper.TipoUsuarioWebMapper;
import br.com.gestrest.api.domain.model.TipoUsuario;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.CriarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.ListarTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.BuscarTipoUsuarioPorIdUseCase;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.ExcluirTipoUsuarioUseCase;
import br.com.gestrest.api.domain.model.ports.in.tipousuario.AtualizarTipoUsuarioUseCase;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TipoUsuarioController.class)
class TipoUsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CriarTipoUsuarioUseCase criarUseCase;

    @MockitoBean
    private AtualizarTipoUsuarioUseCase atualizarUseCase;

    @MockitoBean
    private BuscarTipoUsuarioPorIdUseCase buscarPorIdUseCase;

    @MockitoBean
    private ListarTipoUsuarioUseCase listarUseCase;

    @MockitoBean
    private ExcluirTipoUsuarioUseCase excluirUseCase;

    @MockitoBean
    private TipoUsuarioWebMapper mapper;

    @Test
    void criar_sucesso() throws Exception {
        var req = new CriarTipoUsuarioRequest("GERENTE_RESTAURANTE", "Responsavel pela operacao diaria do restaurante");
        var domain = TipoUsuario.criar("GERENTE_RESTAURANTE");
        var criado = TipoUsuario.existente(3L, "GERENTE_RESTAURANTE");
        var response = new TipoUsuarioResponse(3L, "GERENTE_RESTAURANTE");

        when(mapper.toDomain(any(CriarTipoUsuarioRequest.class))).thenReturn(domain);
        when(criarUseCase.criar(any())).thenReturn(criado);
        when(mapper.toResponse(criado)).thenReturn(response);

        mvc.perform(post("/api/v1/tipos-usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/v1/tipos-usuarios/3"))
                .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.nome").value("GERENTE_RESTAURANTE"));
    }

    @Test
    void buscar_sucesso() throws Exception {
        var domain = TipoUsuario.existente(2L, "DONO_RESTAURANTE");
        var response = new TipoUsuarioResponse(2L, "DONO_RESTAURANTE");

        when(buscarPorIdUseCase.buscarPorId(2L)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(response);

        mvc.perform(get("/api/v1/tipos-usuarios/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
            .andExpect(jsonPath("$.nome").value("DONO_RESTAURANTE"));
    }
}
