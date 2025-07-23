package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Reserva;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class RepositorioDeReservas implements Serializable {
    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    public RepositorioDeReservas() {
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Reserva save(Reserva reserva){
        if (reserva.getId() == null){
            return servicioCrud.create(reserva);
        } else {
            return servicioCrud.update(reserva);
        }
    }

    public Reserva find(@NotNull Long id) throws EntityNotFoundException {
        Reserva reserva = servicioCrud.find(Reserva.class, id);
        if (reserva == null){
            throw new EntityNotFoundException("Reserva no encontrado con [" + id + "]");
        }
        return reserva;
    }
    public List<Reserva> allReservas() throws EntityNotFoundException {
        Map<String, Object> params = new HashMap<>();
        return servicioCrud.findWithNamedQuery("Reserva.findAll", params);
    }

    public List<Habitacion> allHabitaciones() throws EntityNotFoundException {
        Map<String, Object> params = new HashMap<>();
        return servicioCrud.findWithNamedQuery("Reserva.findAll", params);
    }

    public List<Reserva> findByCuidador(Long cuidadorId) {
        Map<String, Object> params = new HashMap<>();
        params.put("cuidadorId", cuidadorId);
        return servicioCrud.findWithNamedQuery("Reserva.findByCuidador", params);
    }
    public List<Reserva> findByMascota(Long mascotaId) {
        Map<String, Object> params = new HashMap<>();
        params.put("mascotaId", mascotaId);
        return servicioCrud.findWithNamedQuery("Reserva.findByMascota", params);
    }

    public List<Reserva> findByRangoDeFechas(LocalDate inicio, LocalDate fin) {
        Map<String, Object> params = new HashMap<>();
        params.put("inicio", inicio);
        params.put("fin", fin);
        return servicioCrud.findWithNamedQuery("Reserva.findInRange", params);
    }



/*
    public Reserva find(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase());
        Reserva reservaEncontrada = (Reserva) servicioCrud.findSingleResultOrNullWithNamedQuery("Reserva.findLikeName", params);
        if (reservaEncontrada == null){
            throw new EntityNotFoundException("Reserva no encontrado con [" + nombre + "]");
        }
        return reservaEncontrada;
    }

    public List<Reserva> findWithLike(@NotNull String nombre) throws EntityNotFoundException{
        Map<String, Object> params = new HashMap<>();
        params.put("nombre", nombre.toLowerCase() + "%");
        return servicioCrud.findWithNamedQuery("Reserva.findLikeName", params);
    }*/
}
