package com.padel.api;

import com.padel.api.dto.CrearReservaRequest;
import com.padel.api.dto.SlotDto;
import com.padel.domain.Cancha;
import com.padel.domain.Reserva;
import com.padel.repository.CanchaRepository;
import com.padel.repository.ReservaRepository;
import com.padel.service.ReservaService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PadelController {

    private final CanchaRepository canchaRepository;
    private final ReservaService reservaService;
    private final ReservaRepository reservaRepository;

    public PadelController(CanchaRepository canchaRepository,
                           ReservaService reservaService,
                           ReservaRepository reservaRepository) {
        this.canchaRepository = canchaRepository;
        this.reservaService = reservaService;
        this.reservaRepository = reservaRepository;
    }

    @GetMapping("/canchas")
    public List<Cancha> listarCanchas() {
        return canchaRepository.findAll();
    }

    @GetMapping("/reservas")
    public List<Reserva> listarReservas(
            @RequestParam Long canchaId,
            @RequestParam LocalDate fecha) {

        return reservaService.listarReservasDeCanchaEnDia(canchaId, fecha);
    }

    @PostMapping("/reservas")
    public Reserva crearReserva(@RequestBody CrearReservaRequest req) {

        return reservaService.crearReserva(
                req.getCanchaId(),
                req.getJugador(),
                req.getFecha(),
                req.getHoraInicio(),
                req.getHoraFin()
        );
    }

    @DeleteMapping("/reservas/{id}")
    public void cancelarReserva(@PathVariable Long id) {
        reservaRepository.deleteById(id);
    }

    // ==============================
    // ENDPOINT AGENDA (CALENDARIO)
    // ==============================

    @GetMapping("/agenda")
    public List<SlotDto> agenda(
            @RequestParam Long canchaId,
            @RequestParam LocalDate fecha,
            @RequestParam(defaultValue = "09:00") LocalTime desde,
            @RequestParam(defaultValue = "23:00") LocalTime hasta,
            @RequestParam(defaultValue = "90") int slotMinutos
    ) {
        if (slotMinutos <= 0) {
            throw new IllegalArgumentException("slotMinutos debe ser > 0");
        }
        if (!desde.isBefore(hasta)) {
            throw new IllegalArgumentException("desde debe ser menor que hasta");
        }

        List<Reserva> reservas = reservaService.listarReservasDeCanchaEnDia(canchaId, fecha);

        List<SlotDto> slots = new ArrayList<>();

        // Número máximo de slots en un día (por ejemplo 09:00-23:00 con 90 min => ~9)
        int minutosTotales = (int) java.time.Duration.between(desde, hasta).toMinutes();
        int numSlots = minutosTotales / slotMinutos;

        for (int i = 0; i < numSlots; i++) {

            LocalTime inicio = desde.plusMinutes((long) i * slotMinutos);
            LocalTime fin = inicio.plusMinutes(slotMinutos);

            Reserva solapada = null;
            for (Reserva r : reservas) {
                boolean solapa = inicio.isBefore(r.getHoraFin()) && fin.isAfter(r.getHoraInicio());
                if (solapa) { solapada = r; break; }
            }

            if (solapada == null) {
                slots.add(new SlotDto(inicio, fin, false, null, null));
            } else {
                slots.add(new SlotDto(inicio, fin, true, solapada.getId(), solapada.getJugador()));
            }
        }

        return slots;
    }
}