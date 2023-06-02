package com.loja.moto.prova.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/cadastrarPage", null).hasRole("ADMIN");
                    auth.requestMatchers("/cadastrarPage", null).hasRole("USER");
                    auth.anyRequest().permitAll();
                })
                .formLogin(login -> login.loginPage("/login").permitAll())
                .logout( logout -> logout.logoutUrl("/logout"))
                .build();
    }
    
    @Bean
    BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}