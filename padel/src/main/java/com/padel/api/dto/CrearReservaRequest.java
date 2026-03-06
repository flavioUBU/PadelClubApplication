package com.padel.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrearReservaRequest {

    @NotNull
    private Long canchaId;

    @NotBlank
    private String jugador;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private LocalTime horaInicio;

    @NotNull
    private LocalTime horaFin;

    public Long getCanchaId() {
        return canchaId;
    }

    public String getJugador() {
        return jugador;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setCanchaId(Long canchaId) {
        this.canchaId = canchaId;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}