package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.EstadoMascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeMascota;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeTareas;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarios;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class FachadaDeEmpleado implements Serializable {

    @Inject
    private RepositorioDeUsuarios repositorioDeUsuarios;

    @Inject
    private RepositorioDeMascota repositorioDeMascotas;

    @Inject
    private RepositorioDeTareas repositorioDeTareas;

    public Usuario obtenerEmpleado(Long id) throws EntityNotFoundException {
        Usuario empleado = repositorioDeUsuarios.find(id);
        if (empleado == null) {
            throw new EntityNotFoundException("Mascota no encontrada con ID: " + id);
        }
        return empleado;
    }

    public List<Habitacion> habitacionesACargo(Long id) throws EntityNotFoundException {
        Usuario empleado = repositorioDeUsuarios.find(id);

        List<Mascota> mascotas = new ArrayList<>(empleado.getMascotasAsignadas());
        List<Habitacion> habitaciones = new ArrayList<>();

        for (Mascota m : mascotas) {
            Habitacion h = m.getHabitacion();
            if (h != null && !habitaciones.contains(h)) {
                habitaciones.add(h);
            }
        }
        return habitaciones;
    }


    public void asignarMascotaAEmpleado(Long mascotaId, Long empleadoId) throws Exception {
        Mascota mascota = repositorioDeMascotas.find(mascotaId);
        Usuario empleado = repositorioDeUsuarios.find(empleadoId);
        if (mascota.getCuidador() != null && mascota.getCuidador().getId().equals(empleadoId)) {
            return;
        }
        mascota.setCuidador(empleado);
        repositorioDeMascotas.save(mascota);
        empleado.getMascotasAsignadas().add(mascota);
        repositorioDeUsuarios.save(empleado);
    }

    public List<Mascota> obtenerMascotasDelEmpleado(Long empleadoId) throws Exception {
        Usuario empleado = repositorioDeUsuarios.find(empleadoId);
        return repositorioDeMascotas.findByCuidador(empleado.getId());
    }

    public List<Tarea> obtenerTareasPendientesDeEmpleado(Long empleadoId) throws Exception {
        List<Mascota> mascotas = obtenerMascotasDelEmpleado(empleadoId);

        return mascotas.stream()
                .flatMap(m -> repositorioDeTareas.findPendientesPorMascota(m.getId()).stream())
                .collect(Collectors.toList());
    }

    public void completarTarea(Long tareaId) throws Exception {
        Tarea tarea = repositorioDeTareas.find(tareaId);
        tarea.setCompletada(true);
        repositorioDeTareas.save(tarea);
    }

    public List<Usuario> obtenerTodosLosEmpleados() {
        return repositorioDeUsuarios.findByRol("EMPLEADO");
    }


    // 5. Reportar al veterinario que una mascota tiene un problema
    /*public void reportarMascotaAVeterinario(Long mascotaId, String observacion, Long empleadoId) throws Exception {
        Mascota mascota = repositorioDeMascotas.find(mascotaId);
        mascota.setEstado(EstadoMascota.EN_OBSERVACION);

        // Aquí podrías generar un Informe o RegistroMedico si tienes esa entidad
        repositorioDeMascotas.saveInformeMedico(mascotaId, empleadoId, observacion);
    }*/
}
