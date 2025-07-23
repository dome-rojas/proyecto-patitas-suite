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
                query = "SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE :nombre"
        ),
        @NamedQuery(
                name = "Usuario.findByRol",
                query = "SELECT u FROM Usuario u WHERE LOWER(u.rol.nombre) = :nombreRol"
        ),
        @NamedQuery(
                name = "Usuario.findAllTareas",
                query = "SELECT DISTINCT t FROM Usuario u JOIN u.tareasAsignadas t"
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
    private String nombre;
    @NotNull @NotEmpty
    private String apellido;
    @NotNull @NotEmpty
    private String correo;
    @NotNull @NotEmpty
    private String clave;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_mascota",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_mascota")
    )
    private Set<Mascota> mascotasAsignadas=new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_tareas",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_tarea"))
    private Set<Tarea> tareasAsignadas=new HashSet<>();
    private boolean primerIngreso;


    public Set<Tarea> getTareasAsignadas() {
        return tareasAsignadas;
    }

    public void setTareasAsignadas(Set<Tarea> tareasAsignadas) {
        this.tareasAsignadas = tareasAsignadas;
    }

    public boolean esPrimerIngreso(){
        return primerIngreso;
    }

    public Long getId() {
        return id;
    }

    public void addMascota(Mascota mascota) {
        mascotasAsignadas.add(mascota);
    }
    public void addTarea(Tarea tarea) {
        tareasAsignadas.add(tarea);
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public Set<Mascota> getMascotasAsignadas() {
        return mascotasAsignadas;
    }

    public void setMascotasAsignadas(Set<Mascota> mascotasAsignadas) {
        this.mascotasAsignadas = mascotasAsignadas;
    }
}
