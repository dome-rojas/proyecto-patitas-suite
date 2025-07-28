package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.TipoDeTarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeTareas;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarioMascotaTarea;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class FachadaDeTareas implements Serializable {

    @Inject
    private RepositorioDeTareas repositorioDeTareas;

    @Inject
    private RepositorioDeUsuarioMascotaTarea repositorioDeUsuarioMascotaTarea;

    public Tarea crearTarea(Tarea tarea) throws Exception {
        return repositorioDeTareas.save(tarea);
    }

    public Tarea actualizarTarea(Tarea tarea) throws Exception {
        return repositorioDeTareas.save(tarea);
    }


    public Tarea buscarPorId(Long tareaId) throws EntityNotFoundException {
        return repositorioDeTareas.find(tareaId);
    }

    public Tarea buscarPorNombre(String nombre) throws EntityNotFoundException {
        return repositorioDeTareas.find(nombre);
    }

    public List<Tarea> todasLasTareas() throws EntityNotFoundException {
        return repositorioDeTareas.allTareas();
    }

    public List<TipoDeTarea> obtenerTiposDeTarea() throws EntityNotFoundException {
        return repositorioDeTareas.allTiposDeTareas();
    }
    public TipoDeTarea obtenerTipoDeTarea(String nombre) throws EntityNotFoundException {
        return repositorioDeTareas.findTipoTarea(nombre);
    }

    // Pendientes de mascota: ahora devuelve asignaciones, no tareas
    public List<UsuarioMascotaTarea> pendientesDeMascota(Long mascotaId) {
        return repositorioDeUsuarioMascotaTarea.findByMascota(mascotaId);
    }

    public List<Tarea> buscarPorNombreConFiltro(String nombre) throws EntityNotFoundException {
        return repositorioDeTareas.findWithLike(nombre);
    }
}
