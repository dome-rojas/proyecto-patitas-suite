package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioEmpleado;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class FachadaDeEmpleado {
    private RepositorioEmpleado empleadoRepo;

    public void asignarMascota(Long empleadoId, Long mascotaId) {}
    public void asignarHabitacion(Long empleadoId, Long habitacionId) {}
}
