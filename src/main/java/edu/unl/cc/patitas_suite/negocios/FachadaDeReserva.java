package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Reserva;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioReserva;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class FachadaDeReserva {
    private RepositorioReserva reservaRepo;

    private FachadaDeHabitacion habitacionRepo;

    public Reserva crearReserva(Reserva reserva) {
        return reserva;
    }
    public void checkIn(Long reservaId) {}
    public void checkOut(Long reservaId) {}
}
