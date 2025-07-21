package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

import java.util.HashSet;
import java.util.Set;

@Stateless
public class RepositorioDeRoles {

    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    public Set<Rol> findAllWithPermissions(){
        return new HashSet<>(servicioCrud.findWithQuery("select * from rol"));
    }

    public Rol find(String nombre){
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
