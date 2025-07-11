package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Cliente;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioCliente;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;
@Named
@RequestScoped
public class FachadaDeCliente {
    private RepositorioCliente clienteRepo;

    public Cliente registrarCliente(Cliente cliente) {
        return cliente;
    }
    public List<Mascota> obtenerMascotasDeCliente(Long clienteId) {
        return null;
    }
}
