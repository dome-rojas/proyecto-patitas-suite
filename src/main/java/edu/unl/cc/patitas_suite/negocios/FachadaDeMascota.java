package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.ExpedienteMedico;
import edu.unl.cc.patitas_suite.dominio.seguridad.AnotacionMedica;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeMascota;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeUsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeExpedienteMedico;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class FachadaDeMascota implements Serializable {

    @Inject
    private RepositorioDeMascota repositorioDeMascota;

    @Inject
    private RepositorioDeUsuarioMascotaTarea repositorioDeUsuarioMascotaTarea;

    @Inject
    private RepositorioDeExpedienteMedico repositorioDeExpedienteMedico;

    public Mascota crearOActualizarMascota(Mascota mascota) throws Exception {
        return repositorioDeMascota.save(mascota);
    }

    public Mascota buscarPorId(Long id) throws EntityNotFoundException {
        return repositorioDeMascota.find(id);
    }

    public List<Mascota> buscarPorNombre(String criterio) throws EntityNotFoundException {
        return repositorioDeMascota.findWithLike(criterio);
    }

    public List<Mascota> obtenerTodas() {
        return repositorioDeMascota.findAll();
    }

    // Obtener tareas asignadas a la mascota por el intermediario
    public List<Tarea> obtenerTareasAsignadas(Long mascotaId) {
        List<UsuarioMascotaTarea> asignaciones = repositorioDeUsuarioMascotaTarea.findByMascota(mascotaId);
        return asignaciones.stream()
                .map(UsuarioMascotaTarea::getTarea)
                .distinct()
                .collect(Collectors.toList());
    }

    // Agregar anotación médica a expediente de mascota
    public void agregarAnotacionMedica(Long mascotaId, String texto, Long usuarioId) throws EntityNotFoundException {
        Mascota mascota = buscarPorId(mascotaId);
        if (mascota.getExpedienteMedico() == null) {
            ExpedienteMedico expediente = new ExpedienteMedico();
            mascota.setExpedienteMedico(expediente);
        }
        ExpedienteMedico expediente = mascota.getExpedienteMedico();

        AnotacionMedica anotacion = new AnotacionMedica();
        anotacion.setTexto(texto);
        anotacion.setFechaHora(LocalDateTime.now());

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        anotacion.setUsuario(usuario);
        anotacion.setExpedienteMedico(expediente);

        expediente.getAnotaciones().add(anotacion);

        repositorioDeExpedienteMedico.save(expediente);
        repositorioDeMascota.save(mascota);
    }
}
