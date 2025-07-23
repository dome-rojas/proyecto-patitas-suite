package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Tarea.findLikeName",
                query = "SELECT t FROM Tarea t WHERE LOWER(t.tipo.nombre) LIKE :nombre"),
        @NamedQuery(
                name = "Tarea.findAll",
                query = "SELECT t FROM Tarea t"),
        @NamedQuery(
                name = "Tarea.findPendientesPorMascota",
                query = "SELECT t FROM Tarea t WHERE t.completada = false AND t.mascota.id = :mascotaId")
})
public class Tarea implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tipo_id")
    @NotNull @NotEmpty
    private TipoDeTarea tipo;
    @NotNull @NotEmpty @Column(name = "fecha_tarea")
    private LocalDate fecha;
    @NotNull @NotEmpty @Column(name = "hora_tarea")
    private LocalTime hora;
    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;
    @NotNull @NotEmpty @Column(name = "completada_tarea")
    private boolean completada;

    public TipoDeTarea getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeTarea tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return Objects.equals(tipo, tarea.tipo) && Objects.equals(fecha, tarea.fecha) && Objects.equals(hora, tarea.hora) && Objects.equals(mascota, tarea.mascota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, fecha, hora, mascota);
    }
}
