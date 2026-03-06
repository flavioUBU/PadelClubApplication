package com.padel.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "canchas")
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private boolean activa = true;

    public Cancha() {}

    public Cancha(String nombre) {
        this.nombre = nombre;
        this.activa = true;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isActiva() { return activa; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setActiva(boolean activa) { this.activa = activa; }
}