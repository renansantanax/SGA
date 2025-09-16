package br.dev.sant.sga.shared.configurations;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.dev.sant.sga.shared.filters.AuthenticationFilter;

@Configuration
public class AuthConfiguration {

	@Bean
	FilterRegistrationBean<AuthenticationFilter> registrationFilter() {

		// Registrando o filter criado para autenticação
		var filter = new FilterRegistrationBean<AuthenticationFilter>();
		filter.setFilter(new AuthenticationFilter());

		// Aplicando o filtro para todos os endpoints da API]
		filter.addUrlPatterns("/api/*");
		

		// retornar o filter
		return filter;
	}
}
