package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Reserva;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.FachadaDeReserva;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class RecepcionistaBean implements Serializable {


    private List<Reserva> reservasDelDia;
    private List<Habitacion> listaHabitaciones;

    @Inject
    private FachadaDeReserva reserva;

    @PostConstruct
    public void init() {
        try {
            this.reservasDelDia = reserva.todasLasReservas();
            this.listaHabitaciones = new ArrayList<>();
            for (Reserva r : reservasDelDia) {
                listaHabitaciones.add(r.getHabitacion());
            }
        }catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reserva> getReservasDelDia() {
        return reservasDelDia;
    }


    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }
}
