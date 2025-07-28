package edu.unl.cc.patitas_suite.dominio.comun;

import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Reserva;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "usuario_mascota_tarea")
@NamedQueries({
        @NamedQuery(name = "UsuarioMascotaTarea.findById",
                query = "SELECT u FROM UsuarioMascotaTarea u WHERE u.id = :id"),
        @NamedQuery(name = "UsuarioMascotaTarea.findByUserPetTask",
                query = "SELECT u FROM UsuarioMascotaTarea u WHERE u.usuario.id = :usuarioId AND u.mascota.id = :mascotaId AND u.tarea.id = :tareaId"),
        @NamedQuery(name = "UsuarioMascotaTarea.findByUserPetTaskDateTime",
                query = "SELECT u FROM UsuarioMascotaTarea u WHERE u.usuario.id = :usuarioId AND u.mascota.id = :mascotaId AND u.tarea.id = :tareaId AND u.fechaAsignacion = :fecha AND u.hora = :hora"),
        @NamedQuery(name = "UsuarioMascotaTarea.findByUsuario",
                query = "SELECT u FROM UsuarioMascotaTarea u WHERE u.usuario.id = :usuarioId"),
        @NamedQuery(name = "UsuarioMascotaTarea.findByMascota",
                query = "SELECT u FROM UsuarioMascotaTarea u WHERE u.mascota.id = :mascotaId"),
        @NamedQuery(name = "UsuarioMascotaTarea.findByReserva",
                query = "SELECT u FROM UsuarioMascotaTarea u WHERE u.reserva.id = :reservaId"),
        @NamedQuery(name = "UsuarioMascotaTarea.findPendientesPorMascota",
                query = "SELECT u FROM UsuarioMascotaTarea u WHERE u.mascota.id = :mascotaId AND u.completada = false")
})
public class UsuarioMascotaTarea implements Serializable {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="mascota_id")
    private Mascota mascota;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="tarea_id")
    private Tarea tarea;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="reserva_id")
    private Reserva reserva;

    private LocalDate fechaAsignacion;
    private LocalTime hora;

    private boolean completada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof UsuarioMascotaTarea)) return false;
        UsuarioMascotaTarea other = (UsuarioMascotaTarea) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}

