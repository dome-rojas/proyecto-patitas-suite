package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.EstadoMascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeMascota;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeTareas;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarios;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class FachadaDeEmpleado {

    @Inject
    private RepositorioDeUsuarios repositorioDeUsuarios;

    @Inject
    private RepositorioDeMascota repositorioDeMascotas;

    @Inject
    private RepositorioDeTareas repositorioDeTareas;

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
