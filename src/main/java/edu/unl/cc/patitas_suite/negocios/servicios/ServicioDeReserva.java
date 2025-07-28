package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Reserva;
import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Stateless
public class ServicioDeReserva implements Serializable {

    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    @Inject
    private RepositorioDeUsuarioMascotaTarea repositorioDeUsuarioMascotaTarea;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Reserva crearReserva(Reserva reserva, List<Tarea> tareasAsignadas) {
        Reserva reservaGuardada = servicioCrud.create(reserva);
        asignarTareasReserva(reservaGuardada, tareasAsignadas);

        return reservaGuardada;
    }

    private void asignarTareasReserva(Reserva reserva, List<Tarea> tareas) {
        if (reserva == null || tareas == null) return;

        for (Tarea tarea : tareas) {
            UsuarioMascotaTarea asignacion = new UsuarioMascotaTarea();
            asignacion.setUsuario(reserva.getCuidador());
            asignacion.setMascota(reserva.getMascota());
            asignacion.setTarea(tarea);
            asignacion.setCompletada(false);
            asignacion.setReserva(reserva);
            repositorioDeUsuarioMascotaTarea.save(asignacion);
        }
    }
    public Reserva crearReservaConTareas(Reserva reserva, List<Tarea> tareas) {
        Reserva r = servicioCrud.create(reserva);
        for (Tarea t : tareas) {
            if(!repositorioDeUsuarioMascotaTarea.existeAsignacion(
                    r.getCuidador().getId(), r.getMascota().getId(), t.getId(), LocalDate.now(), LocalTime.now())) {

                UsuarioMascotaTarea u = new UsuarioMascotaTarea();
                u.setUsuario(r.getCuidador());
                u.setMascota(r.getMascota());
                u.setTarea(t);
                u.setReserva(r);
                u.setFechaAsignacion(LocalDate.now());
                u.setHora(LocalTime.now());
                u.setCompletada(false);
                repositorioDeUsuarioMascotaTarea.save(u);
            }
        }
        return r;
    }
}
