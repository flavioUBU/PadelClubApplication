package com.padel.config;

import com.padel.domain.Cancha;
import com.padel.repository.CanchaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initCanchas(CanchaRepository canchaRepository) {
        return args -> {
            crearSiNoExiste(canchaRepository, "Cancha 1");
            crearSiNoExiste(canchaRepository, "Cancha 2");
            crearSiNoExiste(canchaRepository, "Cancha 3");
        };
    }

    private void crearSiNoExiste(CanchaRepository repo, String nombre) {
        repo.findByNombre(nombre).orElseGet(() -> repo.save(new Cancha(nombre)));
    }
}