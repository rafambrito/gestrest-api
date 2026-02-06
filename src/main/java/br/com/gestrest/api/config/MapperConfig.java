package br.com.gestrest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gestrest.api.adapter.in.web.mapper.TipoUsuarioWebMapper;
import br.com.gestrest.api.adapter.out.persistence.mapper.TipoUsuarioPersistenceMapper;

@Configuration
public class MapperConfig {

    @Bean
    public TipoUsuarioPersistenceMapper tipoUsuarioPersistenceMapper() {
        return new TipoUsuarioPersistenceMapper();
    }

    @Bean
    public TipoUsuarioWebMapper tipoUsuarioWebMapper() {
        return new TipoUsuarioWebMapper();
    }
}
