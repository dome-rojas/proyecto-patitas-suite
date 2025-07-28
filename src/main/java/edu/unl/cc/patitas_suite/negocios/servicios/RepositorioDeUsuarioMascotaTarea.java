package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;

@Stateless
public class RepositorioDeUsuarioMascotaTarea {

    @Inject
    private ServicioDeCrudGenerico servicio;

    public UsuarioMascotaTarea save(UsuarioMascotaTarea entity) {
        if(entity.getId() == null) {
            return servicio.create(entity);
        } else {
            return servicio.update(entity);
        }
    }

    public void delete(UsuarioMascotaTarea entity) {
        if(entity.getId() != null) {
            servicio.delete(UsuarioMascotaTarea.class, entity.getId());
        }
    }

    public UsuarioMascotaTarea findById(Long id) {
        return servicio.find(UsuarioMascotaTarea.class, id);
    }

    public UsuarioMascotaTarea findByUserPetTask(Long usuarioId, Long mascotaId, Long tareaId) {
        Map<String,Object> params = Map.of(
                "usuarioId", usuarioId,
                "mascotaId", mascotaId,
                "tareaId", tareaId
        );
        return servicio.findSingleWithNamedQuery("UsuarioMascotaTarea.findByUserPetTask", params);
    }

    public UsuarioMascotaTarea findByUserPetTaskDateTime(Long usuarioId, Long mascotaId, Long tareaId, LocalDate fecha, LocalTime hora){
        Map<String,Object> params = Map.of(
                "usuarioId", usuarioId,
                "mascotaId", mascotaId,
                "tareaId", tareaId,
                "fecha", fecha,
                "hora", hora
        );
        return servicio.findSingleWithNamedQuery("UsuarioMascotaTarea.findByUserPetTaskDateTime", params);
    }

    public boolean existeAsignacion(Long usuarioId, Long mascotaId, Long tareaId, LocalDate fecha, LocalTime hora){
        return findByUserPetTaskDateTime(usuarioId, mascotaId, tareaId, fecha, hora) != null;
    }

    public List<UsuarioMascotaTarea> findByUsuario(Long usuarioId) {
        return servicio.findWithNamedQuery("UsuarioMascotaTarea.findByUsuario", Map.of("usuarioId", usuarioId));
    }

    public List<UsuarioMascotaTarea> findByMascota(Long mascotaId) {
        return servicio.findWithNamedQuery("UsuarioMascotaTarea.findByMascota", Map.of("mascotaId", mascotaId));
    }

    public List<UsuarioMascotaTarea> findByReserva(Long reservaId) {
        return servicio.findWithNamedQuery("UsuarioMascotaTarea.findByReserva", Map.of("reservaId", reservaId));
    }

    public List<UsuarioMascotaTarea> findPendientesPorMascota(Long mascotaId) {
        return servicio.findWithNamedQuery("UsuarioMascotaTarea.findPendientesPorMascota", Map.of("mascotaId", mascotaId));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarCompletada(Long id) throws EntityNotFoundException {
        UsuarioMascotaTarea u = findById(id);
        if(u == null) throw new EntityNotFoundException("Asignación no encontrada.");
        if(!u.isCompletada()) {
            u.setCompletada(true);
            servicio.update(u);
        }
    }

}
