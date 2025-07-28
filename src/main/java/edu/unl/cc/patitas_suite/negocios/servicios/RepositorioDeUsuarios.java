package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.controladores.AutenticacionBean;
import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Stateless
public class RepositorioDeUsuarios implements Serializable {
    private static Logger logger = Logger.getLogger(AutenticacionBean.class.getName());
    //Se inyecta la dependencia con CrudGenericService
    @Inject
    private ServicioDeCrudGenerico servicioCrud;
    @Inject
    private RepositorioDeUsuarioMascotaTarea repositorioDeUsuarioMascotaTarea;

    public RepositorioDeUsuarios() {
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Usuario save(Usuario usuario){
        if (usuario.getId() == null){
            return servicioCrud.create(usuario);
        } else {
            return servicioCrud.update(usuario);
        }
    }

    public Usuario find(@NotNull Long id) throws EntityNotFoundException {
        Usuario usuario = servicioCrud.find(Usuario.class, id);
        if (usuario == null){
            throw new EntityNotFoundException("Usuario no encontrado con [" + id + "]");
        }
        if(usuario.isActivo())
            return usuario;
        throw new EntityNotFoundException("Usuario no encontrado con [" + id + "]");
    }

    public Usuario find(@NotNull String name) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", name.toLowerCase());
        logger.info("aca perfe ");
        Usuario usuarioEncontrado = servicioCrud.findSingleResultOrNullWithNamedQuery("Usuario.findLikeName", params);
        logger.info("aca la falla ");
        if (usuarioEncontrado == null){
            throw new EntityNotFoundException("Usuario no encontrado con [" + name + "]");
        }
        if(usuarioEncontrado.isActivo())
            return usuarioEncontrado;
        throw new EntityNotFoundException("Usuario no encontrado con nombre: [" + name + "]");
    }

    public List<Tarea> findTareasAsignadasAUsuario(Long usuarioId) {
        List<UsuarioMascotaTarea> asignaciones = repositorioDeUsuarioMascotaTarea.findByUsuario(usuarioId);
        return asignaciones.stream()
                .map(UsuarioMascotaTarea::getTarea)
                .distinct()
                .toList();
    }

    public List<Usuario> findWithLike(@NotNull String nombre) {
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase() + "%");
        return servicioCrud.findWithNamedQuery("Usuario.findLikeName", params);
    }
    public List<Usuario> findByRol(String nombreRol) {
        Map<String, Object> params = new HashMap<>();
        params.put("nombreRol", nombreRol.toLowerCase());
        return servicioCrud.findWithNamedQuery("Usuario.findByRol", params);
    }

    public List<Usuario> findAllUsuarios() {
        return servicioCrud.findWithNamedQuery("Usuario.findAll", new HashMap<>());
    }


}
