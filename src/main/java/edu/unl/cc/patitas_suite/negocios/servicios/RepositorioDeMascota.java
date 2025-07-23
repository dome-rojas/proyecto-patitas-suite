package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class RepositorioDeMascota implements Serializable {

    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    public RepositorioDeMascota() {
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Mascota save(Mascota mascota){
        if (mascota.getId() == null){
            return servicioCrud.create(mascota);
        } else {
            return servicioCrud.update(mascota);
        }
    }

    public Mascota find(@NotNull Long id) throws EntityNotFoundException {
        Mascota mascota = servicioCrud.find(Mascota.class, id);
        if (mascota == null){
            throw new EntityNotFoundException("Mascota no encontrada con [" + id + "]");
        }
        return mascota;
    }

    public Mascota find(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase());
        Mascota mascotaEncontrada = (Mascota) servicioCrud.findSingleResultOrNullWithNamedQuery("Mascota.findLikeName", params);
        if (mascotaEncontrada == null){
            throw new EntityNotFoundException("User no encontrado con [" + nombre + "]");
        }
        return mascotaEncontrada;
    }

    public List<Mascota> findWithLike(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase() + "%");
        return servicioCrud.findWithNamedQuery("Mascota.findLikeName", params);
    }
    public List<Mascota> findByNombreAndPropietario(String nombre, Long propietarioId) {
        Map<String, Object> params = new HashMap<>();
        params.put("propietarioId", propietarioId);
        return servicioCrud.findWithNamedQuery("Mascota.findByNombreAndPropietario", params);
    }

    public List<Mascota> findByCuidador(Long cuidadorId) {
        Map<String, Object> params = new HashMap<>();
        params.put("cuidadorId", cuidadorId);
        return servicioCrud.findWithNamedQuery("Mascota.findByCuidador", params);
    }
    public List<Mascota> findAll() {
        return servicioCrud.findWithNamedQuery("Mascota.findAll", new HashMap<>());
    }


}
