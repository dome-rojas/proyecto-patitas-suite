package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Permiso;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.List;

@Stateless
public class RepositorioDePermiso {

    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    public List<Permiso> findAll(){
        return servicioCrud.findWithNativeQuery("select * from permiso", Permiso.class);
    }

    public Permiso find(Long id) throws EntityNotFoundException {
        Permiso entity = servicioCrud.find(Permiso.class, id);
        if (entity != null) {
            return entity;
        }
        throw new EntityNotFoundException("Permiso no Encontrado [" + id + "]");
    }
}
