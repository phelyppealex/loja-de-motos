package com.loja.moto.prova;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.loja.moto.prova.model.Usuario;
import com.loja.moto.prova.repository.UsuarioRepository;

@SpringBootApplication
public class ProvaApplication implements WebMvcConfigurer {

    @Bean
	CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository, BCryptPasswordEncoder encoder) {
		return args -> {
			List<Usuario> users = new ArrayList<>();
			Usuario admin = new Usuario(1,"ADMINISTRADOR","012.345.678-10","admin", encoder.encode("admin"),true);
			Usuario user1 = new Usuario(2,"João","110.001.110.00","user1", encoder.encode( "user1"),false);
			Usuario user2 = new Usuario(3,"Maria","000.000.111.22","user2",encoder.encode("user2"),false);
			users.add(admin);
			users.add(user1);
			users.add(user2);
			usuarioRepository.saveAll(users);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ProvaApplication.class, args);
	}
	


	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Register resource handler for images
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
    }
}
