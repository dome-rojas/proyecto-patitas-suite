package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Habitacion.findAll",
                query = "SELECT h FROM Habitacion h")
})

public class Habitacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotEmpty
    private String tipo;

    private int capacidad;
    @Enumerated(EnumType.STRING)
    private EstadoHabitacion estado=EstadoHabitacion.LIBRE;

    @ManyToOne
    @JoinColumn(name = "mascota_actual_id")
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

    public EstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoHabitacion estado) {
        this.estado = estado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
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
