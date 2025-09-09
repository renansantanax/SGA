package br.dev.sant.sga.shared.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SGA - Sistema de Gerenciamento de Ativos API")
                        .version("1.0.0")
                        .description("API para o sistema de controle de inventário e ativos de TI da empresa.")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact()
                                .name("Renan Santana")
                                .email("renan@facc10.org.br")
                                .url("https://sant.dev.br")));
    }
}
	
