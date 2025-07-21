package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Reserva;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeReservas;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.util.List;

@Named
@RequestScoped
public class FachadaDeReserva {

    @Inject
    private RepositorioDeReservas repositorioDeReservas;

    // 1. Crear reserva, validando solapamientos y reglas
    public Reserva crearReserva(Reserva reserva) throws Exception {
        return repositorioDeReservas.save(reserva);
    }

    // 2. Actualizar reserva
    public Reserva actualizarReserva(Reserva reserva) throws Exception {
        return repositorioDeReservas.save(reserva);
    }

    public void cancelarReserva(Long id) throws Exception {
        Reserva reserva = repositorioDeReservas.find(id);
        if (reserva == null) {
            throw new EntityNotFoundException("Reserva no encontrada con ID [" + id + "]");
        }
    }

    public Reserva findById(Long id) throws EntityNotFoundException {
        return repositorioDeReservas.find(id);
    }

    public List<Reserva> reservasPorMascota(Long mascotaId) {
        return repositorioDeReservas.findByMascota(mascotaId);
    }

    public List<Reserva> reservasPorCuidador(Long cuidadorId) {
        return repositorioDeReservas.findByCuidador(cuidadorId);
    }

    public List<Reserva> reservasEntreFechas(LocalDate inicio, LocalDate fin) {
        return repositorioDeReservas.findByRangoDeFechas(inicio, fin);
    }

    public List<Reserva> todasLasReservas() throws EntityNotFoundException {
        return repositorioDeReservas.allReservas();
    }

}

