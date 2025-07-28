package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.TipoDeTarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.Query;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Stateless
public class RepositorioDeTareas implements Serializable {
    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    public RepositorioDeTareas() {
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public TipoDeTarea saveTipoTarea(TipoDeTarea tarea){
        if (tarea.getId() == null){
            return servicioCrud.create(tarea);
        } else {
            return servicioCrud.update(tarea);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Tarea save(Tarea tarea){
        if (tarea.getId() == null){
            return servicioCrud.create(tarea);
        } else {
            return servicioCrud.update(tarea);
        }
    }

    public TipoDeTarea findTipoTarea(@NotNull Long id) throws EntityNotFoundException {
        TipoDeTarea tarea = servicioCrud.find(TipoDeTarea.class, id);
        if (tarea == null){
            throw new EntityNotFoundException("Tarea no encontrada con [" + id + "]");
        }
        return tarea;
    }

    public TipoDeTarea findTipoTarea(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase());
        TipoDeTarea tareaEncontrada = (TipoDeTarea) servicioCrud.findSingleResultOrNullWithNamedQuery("TipoDeTarea.findLikeName", params);
        if (tareaEncontrada == null){
            throw new EntityNotFoundException("Tarea no encontrada con [" + nombre + "]");
        }
        return tareaEncontrada;
    }

    public Tarea find(@NotNull Long id) throws EntityNotFoundException {
        Tarea tarea = servicioCrud.find(Tarea.class, id);
        if (tarea == null){
            throw new EntityNotFoundException("Tarea no encontrada con [" + id + "]");
        }
        return tarea;
    }

    public Tarea find(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase());
        Tarea tareaEncontrada = (Tarea) servicioCrud.findSingleResultOrNullWithNamedQuery("Tarea.findLikeName", params);
        if (tareaEncontrada == null){
            throw new EntityNotFoundException("Tarea no encontrada con [" + nombre + "]");
        }
        return tareaEncontrada;
    }

    public List<Tarea> findWithLike(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase() + "%");
        return servicioCrud.findWithNamedQuery("Tarea.findLikeName", params);
    }

    public List<Tarea> allTareas() throws EntityNotFoundException {
        return servicioCrud.findWithNamedQuery("Tarea.findAll", new HashMap<>());
    }

    public List<TipoDeTarea> allTiposDeTareas() throws EntityNotFoundException {
        return servicioCrud.findWithNamedQuery("TipoDeTarea.findAll", new HashMap<>());
    }
    public Tarea findByTipo(TipoDeTarea tipo) {
        Map<String,Object> params = Map.of("tipo", tipo);
        return servicioCrud.findSingleResultOrNullWithNamedQuery("Tarea.findByTipo", params);
    }

}
