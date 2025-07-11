package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

public class Habitacion {
    @Id
    @GeneratedValue
    private Long id;
    private String tipo;
    private int capacidad;
    private String caracteristicas;
    private String estado;

    private Mascota mascotaActual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Mascota getMascotaActual() {
        return mascotaActual;
    }

    public void setMascotaActual(Mascota mascotaActual) {
        this.mascotaActual = mascotaActual;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return capacidad == that.capacidad && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacidad);
    }
}
