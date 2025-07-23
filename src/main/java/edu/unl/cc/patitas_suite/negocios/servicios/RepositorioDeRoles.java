package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class RepositorioDeRoles implements Serializable {

    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    public Set<Rol> findAllWithPermissions(){
        return new HashSet<>(servicioCrud.findWithQuery("select * from rol"));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Rol save(Rol rol){
        if (rol.getId() == null){
            return servicioCrud.create(rol);
        } else {
            return servicioCrud.update(rol);
        }
    }
    public List<Rol> allRoles() throws edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException {
        return servicioCrud.findWithNamedQuery("Rol.findAll", new HashMap<>());
    }
    public Rol findRolId(Long id) throws EntityNotFoundException {
        Rol rol = servicioCrud.find(Rol.class, id);
        if (rol == null){
            throw new EntityNotFoundException("Rol no encontrado con [" + id + "]");
        }
        return rol;
    }
    public Rol find(String nombre) throws EntityNotFoundException {
        String sql = "SELECT * FROM ROL WHERE nombre LIKE ?";
        Query query = servicioCrud.createNativeQuery(sql, Rol.class);
        query.setParameter(1, nombre.toLowerCase());
        Rol entity = (Rol) servicioCrud.findSingleResultOrNullWithQuery(query);
        if (entity != null) {
            return entity;
        }
        throw new EntityNotFoundException("Rol no encontrado con Nombre [" + nombre + "]");
    }
}
