package com.padel.repository;

import com.padel.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByCanchaIdAndFechaOrderByHoraInicioAsc(Long canchaId, LocalDate fecha);

    @Query("""
           select count(r) > 0
           from Reserva r
           where r.cancha.id = :canchaId
             and r.fecha = :fecha
             and (:horaInicio < r.horaFin and :horaFin > r.horaInicio)
           """)
    boolean existeSolape(Long canchaId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin);
}