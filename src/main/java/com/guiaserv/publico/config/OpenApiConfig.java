package com.guiaserv.publico.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI guiaServPublicoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GuiaServ Público API")
                        .description("API REST do sistema GuiaServ Público, responsável por orientar cidadãos na busca por serviços públicos, documentos necessários, unidades de atendimento, horários e avaliações.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nicholas Focke")
                                .email("nicholasfocke05@gmail.com"))
                        .license(new License()
                                .name("Uso acadêmico")));
    }
}