package com.padel.service;

import com.padel.domain.Cancha;
import com.padel.domain.Reserva;
import com.padel.repository.CanchaRepository;
import com.padel.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaService {

    private final CanchaRepository canchaRepository;
    private final ReservaRepository reservaRepository;

    public ReservaService(CanchaRepository canchaRepository, ReservaRepository reservaRepository) {
        this.canchaRepository = canchaRepository;
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> listarReservasDeCanchaEnDia(Long canchaId, LocalDate fecha) {
        return reservaRepository.findByCanchaIdAndFechaOrderByHoraInicioAsc(canchaId, fecha);
    }

    @Transactional
    public Reserva crearReserva(Long canchaId,
                               String jugador,
                               LocalDate fecha,
                               LocalTime horaInicio,
                               LocalTime horaFin) {

        if (jugador == null || jugador.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del jugador es obligatorio.");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        if (horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException("horaInicio y horaFin son obligatorias.");
        }
        if (!horaFin.isAfter(horaInicio)) {
            throw new IllegalArgumentException("horaFin debe ser posterior a horaInicio.");
        }

        Cancha cancha = canchaRepository.findById(canchaId)
                .orElseThrow(() -> new IllegalArgumentException("No existe la cancha con id=" + canchaId));

        if (!cancha.isActiva()) {
            throw new IllegalArgumentException("La cancha está inactiva.");
        }

        boolean solapa = reservaRepository.existeSolape(canchaId, fecha, horaInicio, horaFin);
        if (solapa) {
            throw new IllegalStateException("Ya existe una reserva que se solapa con ese horario.");
        }

        Reserva reserva = new Reserva();
        reserva.setCancha(cancha);
        reserva.setJugador(jugador.trim());
        reserva.setFecha(fecha);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);

        return reservaRepository.save(reserva);
    }
    
    @Transactional
    public void cancelarReserva(Long reservaId) {
        if (reservaId == null) {
            throw new IllegalArgumentException("El id de la reserva es obligatorio.");
        }
        boolean existe = reservaRepository.existsById(reservaId);
        if (!existe) {
            throw new IllegalArgumentException("No existe la reserva con id=" + reservaId);
        }
        reservaRepository.deleteById(reservaId);
    }
}