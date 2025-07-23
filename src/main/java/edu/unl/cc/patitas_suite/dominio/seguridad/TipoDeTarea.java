package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "TipoDeTarea.findLikeName",
                query = "SELECT t FROM TipoDeTarea t WHERE LOWER(t.nombre) LIKE :nombre"
        ),
        @NamedQuery(
                name = "TipoDeTarea.findAll",
                query = "SELECT t FROM TipoDeTarea t"
        )
})
public class TipoDeTarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull @NotEmpty
    private String nombre;
    @NotNull @NotEmpty
    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
