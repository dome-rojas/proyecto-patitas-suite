package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Permiso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long id;

    @NotNull
    private String recurso;   // Ej: "/admin/usuarios"

    @Enumerated(EnumType.STRING)
    private TipoDeAccion accion;

    public Permiso() {
    }

    public Permiso(Long id, String recurso, TipoDeAccion accion) {
        this.id = id;
        this.recurso = recurso;
        this.accion = accion;
    }

    public boolean matchWith(String requestResource, TipoDeAccion requestAction) {
        return this.recurso.equals(requestResource) &&
                this.accion.equals(requestAction);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Permiso that = (Permiso) o;
        return Objects.equals(id, that.id) && Objects.equals(recurso, that.recurso) && accion == that.accion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recurso, accion);
    }
}
