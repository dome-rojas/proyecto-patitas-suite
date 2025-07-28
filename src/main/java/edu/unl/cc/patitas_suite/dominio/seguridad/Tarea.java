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
                name = "Tarea.findByTipo",
                query = "SELECT t FROM Tarea t WHERE t.tipo = :tipo"
        )
})
public class Tarea implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private TipoDeTarea tipo;
    public Tarea() {}
    public Tarea(TipoDeTarea tipo) {
        this.tipo = tipo;
    }
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return Objects.equals(tipo, tarea.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo);
    }
}
