package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Cliente;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
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

@Stateless
public class RepositorioDeClientes implements Serializable {
    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    public RepositorioDeClientes() {
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Cliente save(Cliente cliente){
        if (cliente.getId() == null){
            return servicioCrud.create(cliente);
        } else {
            return servicioCrud.update(cliente);
        }
    }

    public Cliente find(@NotNull Long id) throws EntityNotFoundException {
        Cliente cliente = servicioCrud.find(Cliente.class, id);
        if (cliente == null){
            throw new EntityNotFoundException("Cliente no encontrado con [" + id + "]");
        }
        return cliente;
    }

    public Cliente find(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase());
        Cliente clienteEncontrado = (Cliente) servicioCrud.findSingleResultOrNullWithNamedQuery("Cliente.findLikeName", params);
        if (clienteEncontrado == null){
            throw new EntityNotFoundException("Cliente no encontrado con [" + nombre + "]");
        }
        return clienteEncontrado;
    }

    public List<Cliente> findWithLike(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase() + "%");
        return servicioCrud.findWithNamedQuery("Cliente.findLikeName", params);
    }

    public List<Cliente> findAll() {
        return servicioCrud.findWithNamedQuery("Cliente.findAll", new HashMap<>());
    }

}
