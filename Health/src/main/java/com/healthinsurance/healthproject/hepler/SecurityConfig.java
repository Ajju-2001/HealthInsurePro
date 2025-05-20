package com.healthinsurance.healthproject.hepler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
public class SecurityConfig {
	private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean 
    //Declares a SecurityFilterChain bean that configures the HTTP security.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth //authorizeHttpRequests is a method used to define which endpoints are accessible by whom
                                .requestMatchers(
                                		 "/users/register", //Allows new users to register — public so users can sign up
                                		    "/users/login", //Allows users to log in — public so credentials can be submitted
                                		    "/swagger-ui/**", //Gives access to Swagger UI resources 
                                		    "/swagger-ui.html", //Direct URL to Swagger's main interface
                                		    "/v3/api-docs/**", //API documentation in JSON format 
                                		    "/swagger-resources/**", //Internal Swagger resources
                                		    "/webjars/**", //Static assets like JavaScript and CSS used by Swagger
                                		    "/v3/api-docs"  //Sometimes Swagger uses this exact path
                                ).permitAll() //Permit access to these URL patterns
                                .anyRequest().authenticated() //used in Spring Security to enforce authentication on all remaining routes that are not explicitly allowed 
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        //returns the SecurityFilterChain bean that Spring Boot uses to configure the actual security settings for your app.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
