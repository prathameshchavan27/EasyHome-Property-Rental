package com.prs.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {
	
	@Value("${frontend.url}")
	private String url;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .cors(cors -> cors.configurationSource(request -> {
	            CorsConfiguration config = new CorsConfiguration();
	            config.setAllowedOrigins(List.of(url));
	            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	            config.setAllowedHeaders(List.of("*"));
	            config.setAllowCredentials(true);
	            return config;
	        }))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
	            .requestMatchers("/images/**").permitAll()
	            .requestMatchers(
                        "/swagger-ui/**",       // Swagger UI
                        "/v3/api-docs/**",     // OpenAPI documentation
                        "/swagger-resources/**", // Swagger resources
                        "/webjars/**"          // WebJars used by Swagger
                ).permitAll()// Allow preflight OPTIONS requests
	            .requestMatchers("login", "register").permitAll() // Public endpoints
	            .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-specific endpoints
	            .requestMatchers("/landlord/**").hasAnyRole("LANDLORD", "ADMIN") // Landlord access
	            .requestMatchers("/tenant/**").hasAnyRole("TENANT", "ADMIN") // Tenant access
	            .requestMatchers("/bookings/**").hasAnyRole("LANDLORD","TENANT", "ADMIN") // Tenant access
	            .requestMatchers("/users/**").hasAnyRole("LANDLORD","TENANT", "ADMIN") 
	            .requestMatchers("/properties/**").permitAll() // Tenant access
	            .requestMatchers("/webhook/**").permitAll()
	            .requestMatchers("/password/**").permitAll()
	            .anyRequest().authenticated() // Protect other endpoints
	        )
	        .httpBasic(Customizer.withDefaults()) // Enable optional HTTP Basic Authentication
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

	    return http.build();
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