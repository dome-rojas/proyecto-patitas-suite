package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.TipoDeTarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeTareas;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.util.List;

@Named
@RequestScoped
public class FachadaDeTareas {

    @Inject
    private RepositorioDeTareas repositorioDeTareas;

    // 1. Crear nueva tarea
    public Tarea crearTarea(Tarea tarea) throws Exception {
        return repositorioDeTareas.save(tarea);
    }

    public Tarea actualizarTarea(Tarea tarea) throws Exception {
        return repositorioDeTareas.save(tarea);
    }

    public void marcarComoCompletada(Long tareaId) throws Exception {
        Tarea tarea = repositorioDeTareas.find(tareaId);
        tarea.setCompletada(true);
        repositorioDeTareas.save(tarea);
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

    public List<Tarea> pendientesDeMascota(Long mascotaId) {
        return repositorioDeTareas.findPendientesPorMascota(mascotaId);
    }

    public List<Tarea> buscarPorNombreConFiltro(String nombre) throws EntityNotFoundException {
        return repositorioDeTareas.findWithLike(nombre);
    }

}
