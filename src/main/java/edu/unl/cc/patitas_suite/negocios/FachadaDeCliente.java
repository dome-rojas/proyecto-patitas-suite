package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Cliente;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeClientes;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
@Stateless
public class FachadaDeCliente implements Serializable {

    @Inject
    private RepositorioDeClientes repositorioDeClientes;

    public Cliente guardar(Cliente cliente) throws Exception {
        try {
            Cliente existente = repositorioDeClientes.find(cliente.getNombre());
            if (!existente.getContacto().equals(cliente.getContacto())) {
                throw new Exception("Ya existe un cliente con ese nombre pero diferente contacto");
            }
            return existente;
        } catch (EntityNotFoundException e) {
            return repositorioDeClientes.save(cliente);
        }
    }

    public Cliente buscarPorId(Long id) throws EntityNotFoundException {
        return repositorioDeClientes.find(id);
    }

    public Cliente buscarPorNombre(String nombre) throws EntityNotFoundException {
        return repositorioDeClientes.find(nombre);
    }

    public List<Cliente> listarClientes(String filtroNombre) throws EntityNotFoundException {
        return repositorioDeClientes.findWithLike(filtroNombre);
    }

    public Cliente actualizarDatosContacto(Long id, String nuevoContacto, String nuevaDireccion) throws Exception {
        Cliente cliente = repositorioDeClientes.find(id);
        cliente.setContacto(nuevoContacto);
        cliente.setDireccion(nuevaDireccion);
        return repositorioDeClientes.save(cliente);
    }
}
