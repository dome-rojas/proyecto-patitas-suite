package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.EstadoHabitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeHabitaciones;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class FachadaDeHabitacion {
    @Inject
    private RepositorioDeHabitaciones repositorioDeHabitaciones;

    // 1. Crear o actualizar una habitación
    public Habitacion guardar(Habitacion habitacion) throws Exception {
        return repositorioDeHabitaciones.save(habitacion);
    }

    // 2. Buscar habitación por ID
    public Habitacion buscarPorId(Long id) throws EntityNotFoundException {
        return repositorioDeHabitaciones.find(id);
    }

    // 3. Listar todas las habitaciones
    public List<Habitacion> todasLasHabitaciones() {
        return repositorioDeHabitaciones.findAll();
    }

    // 4. Listar solo habitaciones libres
    public List<Habitacion> habitacionesDisponibles() {
        return todasLasHabitaciones().stream()
                .filter(h -> h.getEstado() == EstadoHabitacion.LIBRE)
                .collect(Collectors.toList());
    }

    // 5. Ocupar habitación (marcar estado y asignar mascota)
    public Habitacion ocuparHabitacion(Long habitacionId, Mascota mascota) throws Exception {
        Habitacion habitacion = repositorioDeHabitaciones.find(habitacionId);
        if (habitacion.getEstado() != EstadoHabitacion.LIBRE) {
            throw new IllegalStateException("La habitación no está disponible.");
        }
        habitacion.setMascotaActual(mascota);
        habitacion.setEstado(EstadoHabitacion.OCUPADA);
        return repositorioDeHabitaciones.save(habitacion);
    }

    // 6. Liberar habitación (quitar mascota y marcar como LIBRE)
    public Habitacion liberarHabitacion(Long habitacionId) throws Exception {
        Habitacion habitacion = repositorioDeHabitaciones.find(habitacionId);
        habitacion.setMascotaActual(null);
        habitacion.setEstado(EstadoHabitacion.LIBRE);
        return repositorioDeHabitaciones.save(habitacion);
    }

    // 7. Cambiar estado manualmente (opcional)
    public Habitacion cambiarEstado(Long id, EstadoHabitacion nuevoEstado) throws Exception {
        Habitacion habitacion = repositorioDeHabitaciones.find(id);
        habitacion.setEstado(nuevoEstado);
        return repositorioDeHabitaciones.save(habitacion);
    }

    // 8. Filtrar por tipo (ej: pequeña, grande)
    public List<Habitacion> habitacionesPorTipo(String tipo) {
        return todasLasHabitaciones().stream()
                .filter(h -> Objects.nonNull(h.getTipo()) && h.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    // 9. Filtrar por capacidad
    public List<Habitacion> habitacionesPorCapacidad(int capacidadMinima) {
        return todasLasHabitaciones().stream()
                .filter(h -> h.getCapacidad() >= capacidadMinima)
                .collect(Collectors.toList());
    }

}
