package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class RepositorioDeUsuarios {

    //Se inyecta la dependencia con CrudGenericService
    @Inject
    private ServicioDeCrudGenerico servicioCrud;

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
        return usuario;
    }

    public Usuario find(@NotNull String name) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", name.toLowerCase());
        Usuario usuarioEncontrado = servicioCrud.findSingleResultOrNullWithNamedQuery("Usuario.findLikeName", params);
        if (usuarioEncontrado == null){
            throw new EntityNotFoundException("Usuario no encontrado con [" + name + "]");
        }
        return usuarioEncontrado;
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
    public List<Tarea> allTareas() throws EntityNotFoundException {
        return servicioCrud.findWithNamedQuery("Usuario.findAll", new HashMap<>());
    }
    public List<Usuario> findAllUsuarios() {
        return servicioCrud.findWithNamedQuery("Usuario.findAll", new HashMap<>());
    }


}
