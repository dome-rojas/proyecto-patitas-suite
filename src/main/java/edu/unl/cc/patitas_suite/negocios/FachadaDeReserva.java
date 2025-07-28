package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Reserva;
import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.TipoDeTarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeReservas;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeTareas;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarioMascotaTarea;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Stateless
public class FachadaDeReserva implements Serializable {

    @Inject
    private RepositorioDeReservas repositorioDeReservas;

    @Inject
    private RepositorioDeUsuarioMascotaTarea repositorioDeUsuarioMascotaTarea;
    @Inject
    private RepositorioDeTareas repositorioDeTareas;

    public List<Reserva> todasLasReservas() throws EntityNotFoundException {
        return repositorioDeReservas.allReservas();
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Reserva crearReservaConTareas(Reserva reserva, List<Tarea> tareasGenerales) throws Exception {
        Reserva reservaGuardada = repositorioDeReservas.save(reserva);
        Mascota mascota = reservaGuardada.getMascota();
        Usuario cuidador = reservaGuardada.getCuidador();
        // Asignar tarea "dar de comer" dos veces según complexión
        TipoDeTarea tipoAlimentar = repositorioDeTareas.findTipoTarea("ALIMENTAR");
        asignarTareasDarDeComer(cuidador, mascota, reservaGuardada, tipoAlimentar);
        // Asignar otras tareas generales
        if (tareasGenerales != null) {
            for (Tarea tareaCatalogo : tareasGenerales) {
                // puedes obtener su tipo desde tareaCatalogo.getTipo();
                asignarTareaGenerica(cuidador, mascota, reservaGuardada, tareaCatalogo.getTipo(), LocalDate.now(), LocalTime.now());
            }
        }
        return reservaGuardada;
    }

    private void asignarTareasDarDeComer(Usuario usuario, Mascota mascota, Reserva reserva, TipoDeTarea tipoAlimentar) throws Exception {
        LocalDate fecha = reserva.getFechaEntrada();
        LocalTime hora1 = LocalTime.of(8, 0);
        int horasADespues = switch (mascota.getComplexion()) {
            case GRANDE -> 6;
            case MEDIANO -> 8;
            case CHICO -> 10;
            default -> 8;
        };
        LocalTime hora2 = hora1.plusHours(horasADespues);

        asignarTarea(usuario, mascota, reserva, tipoAlimentar, fecha, hora1);
        asignarTarea(usuario, mascota, reserva, tipoAlimentar, fecha, hora2);
    }

    private void asignarTarea(Usuario usuario, Mascota mascota, Reserva reserva, TipoDeTarea tipo, LocalDate fecha, LocalTime hora) throws Exception {
        Tarea tarea = new Tarea();
        tarea.setTipo(tipo);
        tarea = repositorioDeTareas.save(tarea);
        if (!repositorioDeUsuarioMascotaTarea.existeAsignacion(usuario.getId(), mascota.getId(), tarea.getId(), fecha, hora)){
            UsuarioMascotaTarea asignacion = new UsuarioMascotaTarea();
            asignacion.setUsuario(usuario);
            asignacion.setMascota(mascota);
            asignacion.setTarea(tarea);
            asignacion.setReserva(reserva);
            asignacion.setFechaAsignacion(fecha);
            asignacion.setHora(hora);
            asignacion.setCompletada(false);
            repositorioDeUsuarioMascotaTarea.save(asignacion);
        }
    }


    public void asignarTareaGenerica(
            Usuario usuario, Mascota mascota, Reserva reserva, TipoDeTarea tipo, LocalDate fecha, LocalTime hora) throws Exception {
        asignarTarea(usuario, mascota, reserva, tipo, fecha, hora);
    }

    public Reserva actualizarReserva(Reserva reserva) throws Exception {
        return repositorioDeReservas.save(reserva);
    }

    public Reserva buscarPorId(Long id) throws EntityNotFoundException {
        return repositorioDeReservas.find(id);
    }

}
