package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Cliente;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
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
public class RepositorioDeHabitaciones implements Serializable {
    @Inject
    private ServicioDeCrudGenerico servicioCrud;

    public RepositorioDeHabitaciones() {
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Habitacion save(Habitacion habitacion){
        if (habitacion.getId() == null){
            return servicioCrud.create(habitacion);
        } else {
            return servicioCrud.update(habitacion);
        }
    }

    public Habitacion find(@NotNull Long id) throws EntityNotFoundException {
        Habitacion habitacion = servicioCrud.find(Habitacion.class, id);
        if (habitacion == null){
            throw new EntityNotFoundException("Cliente no encontrado con [" + id + "]");
        }
        return habitacion;
    }
    public List<Habitacion> findAll() {
        return servicioCrud.findWithNamedQuery("Habitacion.findAll", new HashMap<>());
    }


}
