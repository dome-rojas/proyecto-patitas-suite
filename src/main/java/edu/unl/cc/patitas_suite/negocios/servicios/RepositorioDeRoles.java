package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Cliente;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;

@Stateless
public class RepositorioDeRoles {

    @Inject
    private ServicioDeCrudGenerico servicioDeCrud;

    public Rol save(Rol rol) {
        return rol.getId() == null ? servicioDeCrud.create(rol) : servicioDeCrud.update(rol);
    }

    public Rol findById(Long id) {
        return servicioDeCrud.find(Rol.class, id);
    }

    public Rol findByNombre(String nombre) throws EntityNotFoundException {
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase());
        Rol rolEncontrado = (Rol) servicioDeCrud.findSingleResultOrNullWithNamedQuery("Rol.findByNombre", params);
        if (rolEncontrado == null) {
            throw new EntityNotFoundException("Cliente no encontrado con [" + nombre + "]");
        }
        return rolEncontrado;
    }

    public List<Rol> findAll() {
        return servicioDeCrud.findWithNamedQuery("Rol.findAll", Map.of());
    }
}

