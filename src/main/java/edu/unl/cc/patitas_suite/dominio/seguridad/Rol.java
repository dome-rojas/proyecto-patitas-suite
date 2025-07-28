package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r"),
        @NamedQuery(name = "Rol.findByNombre", query = "SELECT r FROM Rol r WHERE LOWER(r.nombre) = :nombre")
})

public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    @Column(unique = true,nullable = false,length = 50)
    private String nombre;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_Permision",
            joinColumns=@JoinColumn(name="role_id"),
            inverseJoinColumns=@JoinColumn(name="permission_id"))
    private Set<Permiso> permisos;

    public Rol() {
        permisos = new HashSet<>();
    }

    public Rol(Long id, String nombre) {
        this();
        this.id = id;
        this.nombre = nombre;
    }

    public void add(Permiso permiso){
        permisos.add(permiso);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permissions) {
        this.permisos = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rol role = (Rol) o;
        return Objects.equals(id, role.id) && Objects.equals(nombre, role.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
