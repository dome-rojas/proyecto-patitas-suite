package edu.unl.cc.patitas_suite.dominio.comun;

import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Usuario.findLikeName",
                query = "SELECT u FROM Usuario u WHERE LOWER(u.userName) LIKE :nombre"
        ),
        @NamedQuery(
                name = "Usuario.findByRol",
                query = "SELECT u FROM Usuario u WHERE LOWER(u.rol.nombre) = :nombreRol"
        ),
        @NamedQuery(
                name = "Usuario.findAll",
                query = "SELECT u FROM Usuario u"
        )
})

public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotEmpty
    private String userName;
    @NotNull @NotEmpty
    private String nombreCompleto;
    @NotNull @NotEmpty
    private String correo;
    @NotNull @NotEmpty
    private String clave;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioMascotaTarea> asignaciones = new HashSet<>();

    private boolean primerIngreso;
    private boolean activo=true;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<UsuarioMascotaTarea> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(Set<UsuarioMascotaTarea> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public boolean isPrimerIngreso() {
        return primerIngreso;
    }

    public boolean esPrimerIngreso(){
        return primerIngreso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String nombre) {
        this.userName = nombre;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String apellido) {
        this.nombreCompleto = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }


    public void setPrimerIngreso(boolean primerIngreso) {
        this.primerIngreso = primerIngreso;
    }

}
