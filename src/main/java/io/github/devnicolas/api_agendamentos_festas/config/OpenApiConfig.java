package io.github.devnicolas.api_agendamentos_festas.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT Token via Keycloak OAuth2")))
                .info(new Info()
                        .title("API de Agendamentos de Festas")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de agendamentos de festas e eventos")
                        .contact(new Contact()
                                .name("DevNicolas")
                                .url("https://github.com/devnicolas")
                                .email("dev@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("http://springdoc.org")));
    }
}

