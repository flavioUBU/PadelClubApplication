package com.padel.api.dto;

import java.time.LocalTime;

public class SlotDto {

    private LocalTime inicio;
    private LocalTime fin;
    private boolean ocupado;
    private Long reservaId;
    private String jugador;

    public SlotDto() {}

    public SlotDto(LocalTime inicio, LocalTime fin, boolean ocupado, Long reservaId, String jugador) {
        this.inicio = inicio;
        this.fin = fin;
        this.ocupado = ocupado;
        this.reservaId = reservaId;
        this.jugador = jugador;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public Long getReservaId() {
        return reservaId;
    }

    public void setReservaId(Long reservaId) {
        this.reservaId = reservaId;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
}