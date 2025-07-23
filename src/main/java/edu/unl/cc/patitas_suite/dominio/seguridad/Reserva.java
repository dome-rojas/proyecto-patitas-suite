package edu.unl.cc.patitas_suite.dominio.seguridad;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Reserva.findByCuidador",
                query = "SELECT r FROM Reserva r WHERE r.cuidador.id = :cuidadorId"),
        @NamedQuery(
                name = "Reserva.findByMascota",
                query = "SELECT r FROM Reserva r WHERE r.mascota.id = :mascotaId"),
        @NamedQuery(
                name = "Reserva.findAll",
                query = "SELECT r FROM Reserva r"),
        @NamedQuery(
                name = "Reserva.findInRange",
                query = "SELECT r FROM Reserva r WHERE r.fechaEntrada BETWEEN :inicio AND :fin")
})
public class Reserva implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @Column(name = "fecha_entrada")
    private LocalDate fechaEntrada;
    @NotNull @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @ManyToOne
    @JoinColumn(name = "cuidador_id")
    private Usuario cuidador;

    @OneToOne @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @OneToOne @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;

    public Usuario getCuidador() {
        return cuidador;
    }

    public void setCuidador(Usuario cuidador) {
        this.cuidador = cuidador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
}
