package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioHabitacion;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;
@Named
@RequestScoped
public class FachadaDeHabitacion {
    private RepositorioHabitacion habitacionRepo;

    public List<Habitacion> obtenerDisponibles() {
        return null;
    }
    public void marcarLimpia(Long habitacionId) {}
}
