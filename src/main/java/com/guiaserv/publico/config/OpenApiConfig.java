package com.guiaserv.publico.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI guiaServPublicoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GuiaServ Público API")
                        .description("API REST do sistema GuiaServ Público, responsável por orientar cidadãos na busca por serviços públicos, documentos necessários, unidades de atendimento, horários e avaliações.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nicholas Focke")
                                .email("seu-email@email.com"))
                        .license(new License()
                                .name("Uso acadêmico")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(
                                SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }
}