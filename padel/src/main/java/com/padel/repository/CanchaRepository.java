package com.padel.repository;

import com.padel.domain.Cancha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CanchaRepository extends JpaRepository<Cancha, Long> {

    Optional<Cancha> findByNombre(String nombre);

}