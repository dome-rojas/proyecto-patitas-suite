package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarios;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class FachadaDeEmpleado implements Serializable {

    @Inject
    private RepositorioDeUsuarios repositorioDeUsuarios;

    @Inject
    private RepositorioDeUsuarioMascotaTarea repositorioDeUsuarioMascotaTarea;

    public Usuario deshabilitarUsuario(Usuario usuario) throws Exception {

        return repositorioDeUsuarios.save(usuario);
    }

    public Usuario guardarEmpleado(Usuario usuario) throws Exception {
        return repositorioDeUsuarios.save(usuario);
    }

    public Usuario obtenerEmpleadoPorId(Long id) throws EntityNotFoundException {
        Usuario usuario = repositorioDeUsuarios.find(id);
        if(usuario == null) throw new EntityNotFoundException("Usuario no encontrado con id " + id);
        return usuario;
    }

    public List<Usuario> obtenerEmpleadosPorRol(String rol) {
        return repositorioDeUsuarios.findByRol(rol);
    }

    public List<Usuario> obtenerTodosLosEmpleados() {
        List<Usuario> usuarios =repositorioDeUsuarios.findAllUsuarios();
        Iterator<Usuario> iter = usuarios.iterator();
        while(iter.hasNext()) {
            Usuario u = iter.next();
            if(!"Empleado".equalsIgnoreCase(u.getRol().getNombre())) {
                iter.remove();
            }
        }
        return usuarios;
    }

    // Obtener todas las tareas asignadas a un usuario (empleado)
    public List<Tarea> obtenerTareasAsignadas(Long usuarioId) throws EntityNotFoundException {
        List<UsuarioMascotaTarea> asignaciones = repositorioDeUsuarioMascotaTarea.findByUsuario(usuarioId);
        return asignaciones.stream()
                .map(UsuarioMascotaTarea::getTarea)
                .distinct()
                .collect(Collectors.toList());
    }
    public List<Mascota> obtenerMascotasAsignadas(Long usuarioId) throws EntityNotFoundException {
        List<UsuarioMascotaTarea> asignaciones = repositorioDeUsuarioMascotaTarea.findByUsuario(usuarioId);
        return asignaciones.stream()
                .map(UsuarioMascotaTarea::getMascota)
                .distinct()
                .collect(Collectors.toList());
    }
    public List<Habitacion> obtenerHabitacionesAsignadas(Long usuarioId) throws EntityNotFoundException {
        List<UsuarioMascotaTarea> asignaciones = repositorioDeUsuarioMascotaTarea.findByUsuario(usuarioId);
        List<Mascota> mascotas = asignaciones.stream()
                .map(UsuarioMascotaTarea::getMascota)
                .distinct()
                .toList();
        return mascotas.stream()
                .map(Mascota::getHabitacion)
                .distinct()
                .collect(Collectors.toList());
    }

    // Completar tarea específica
    public void completarTarea(Long usuarioId, Long mascotaId, Long tareaId) throws EntityNotFoundException {
        UsuarioMascotaTarea usr= repositorioDeUsuarioMascotaTarea.findByUserPetTask(usuarioId, mascotaId, tareaId);
        repositorioDeUsuarioMascotaTarea.marcarCompletada(usr.getId());
    }
}
