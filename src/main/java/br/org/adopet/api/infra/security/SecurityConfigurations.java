package br.org.adopet.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeHttpRequests()
				.requestMatchers(HttpMethod.POST, "/login", "/tutores", "/abrigos").permitAll()
				.requestMatchers(HttpMethod.PUT, "/abrigos").hasRole("ABRIGO")
				.requestMatchers(HttpMethod.PATCH, "/abrigos").hasRole("ABRIGO")
				.requestMatchers(HttpMethod.PUT, "/tutores").hasRole("TUTOR")
				.requestMatchers(HttpMethod.PATCH, "/tutores").hasRole("TUTOR")
				.requestMatchers(HttpMethod.GET, "/pets/**").authenticated()
				.requestMatchers(HttpMethod.POST, "/pets").hasRole("ABRIGO")
				.requestMatchers(HttpMethod.PUT, "/pets").hasRole("ABRIGO")
				.requestMatchers(HttpMethod.PATCH, "/pets").hasRole("ABRIGO")
				.requestMatchers(HttpMethod.DELETE, "/pets/**").hasRole("ABRIGO")
				.requestMatchers(HttpMethod.POST, "/adocao").hasRole("TUTOR")
				.requestMatchers(HttpMethod.DELETE, "/adocao/**").hasRole("ABRIGO")
				.anyRequest().hasRole("ADMIN")
				.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
