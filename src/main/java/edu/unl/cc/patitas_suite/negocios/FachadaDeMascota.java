package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioHabitacion;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioMascota;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;
@Named
@RequestScoped
public class FachadaDeMascota {

    private RepositorioMascota mascotaRepo;

    private RepositorioHabitacion habitacionRepo;

    public Mascota registrarMascota(Mascota mascota) {
        return mascota;
    }
    public Mascota editarMascota(Mascota mascota) {
        return mascota;
    }
    public void asignarHabitacion(Long mascotaId, Long habitacionId) {

    }
    public List<Mascota> obtenerMascotasPorEstado(String estado) {
        return null;
    }
}
