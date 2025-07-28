package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeTareas;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarioMascotaTarea;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Stateless
public class FachadaUsuarioMascotaTarea {

    @Inject
    private RepositorioDeUsuarioMascotaTarea repositorio;

    @Inject
    private RepositorioDeTareas repositorioTarea;

    /** Guarda o actualiza una asignación */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public UsuarioMascotaTarea saveAsignacion(UsuarioMascotaTarea asignacion) throws Exception {
        // Aquí puedes agregar validaciones de negocio si quieres
        return repositorio.save(asignacion);
    }

    /** Elimina una asignación */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void eliminarAsignacion(Long asignacionId) throws Exception {
        UsuarioMascotaTarea asignacion = repositorio.findById(asignacionId);
        if (asignacion != null) {
            repositorio.delete(asignacion);
        } else {
            throw new EntityNotFoundException("Asignación no encontrada id=" + asignacionId);
        }
    }

    /** Busca asignación por ID de UsuarioMascotaTarea */
    public UsuarioMascotaTarea findById(Long id) {
        return repositorio.findById(id);
    }

    /** Busca asignación por usuario, mascota, tarea y fecha/hora */
    public UsuarioMascotaTarea findByUserPetTaskDateTime(Long usuarioId, Long mascotaId, Long tareaId, LocalDate fecha, LocalTime hora) {
        return repositorio.findByUserPetTaskDateTime(usuarioId, mascotaId, tareaId, fecha, hora);
    }

    /** Busca asignación general por usuario, mascota, tarea (sin fecha/hora) */
    public UsuarioMascotaTarea findByUserPetTask(Long usuarioId, Long mascotaId, Long tareaId) {
        return repositorio.findByUserPetTask(usuarioId, mascotaId, tareaId);
    }

    /** Marca la asignación como completada */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarComoCompletada(Long usuarioId, Long mascotaId, Long tareaId, LocalDate fecha, LocalTime hora) throws EntityNotFoundException {
        UsuarioMascotaTarea asignacion = repositorio.findByUserPetTaskDateTime(usuarioId, mascotaId, tareaId, fecha, hora);
        if (asignacion == null) {
            throw new EntityNotFoundException("Asignación no encontrada para marcar completada");
        }
        if (!asignacion.isCompletada()) {
            asignacion.setCompletada(true);
            repositorio.save(asignacion);
        }
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarComoCompletada(Long id) throws EntityNotFoundException {
        UsuarioMascotaTarea asignacion = repositorio.findById(id);
        if (asignacion == null) {
            throw new EntityNotFoundException("Asignación no encontrada para marcar completada");
        }
        if (!asignacion.isCompletada()) {
            asignacion.setCompletada(true);
            repositorio.save(asignacion);
        }
    }

    /** Verifica si existe una asignación con los parámetros */
    public boolean existeAsignacion(Long usuarioId, Long mascotaId, Long tareaId, LocalDate fecha, LocalTime hora) {
        return repositorio.existeAsignacion(usuarioId, mascotaId, tareaId, fecha, hora);
    }

    /** Obtiene todas las asignaciones para un usuario concreto */
    public List<UsuarioMascotaTarea> obtenerAsignacionesPorUsuario(Long usuarioId) {
        return repositorio.findByUsuario(usuarioId);
    }

    /** Obtiene todas las asignaciones para una mascota */
    public List<UsuarioMascotaTarea> obtenerAsignacionesPorMascota(Long mascotaId) {
        return repositorio.findByMascota(mascotaId);
    }

    /** Obtiene todas las asignaciones relacionadas a una reserva */
    public List<UsuarioMascotaTarea> obtenerAsignacionesPorReserva(Long reservaId) {
        return repositorio.findByReserva(reservaId);
    }

}