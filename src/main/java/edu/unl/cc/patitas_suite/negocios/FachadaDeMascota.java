package edu.unl.cc.patitas_suite.negocios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.excepciones.MascotaDuplicadaException;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeMascota;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Stateless
public class FachadaDeMascota implements Serializable {

    @Inject
    private RepositorioDeMascota repositorioDeMascota;

    public Mascota create(Mascota mascota) throws Exception {
        validarDuplicado(mascota);
        return repositorioDeMascota.save(mascota);
    }

    public Mascota update(Mascota mascota) throws Exception {
        if (mascota.getId() == null) {
            return create(mascota);
        }
        Mascota mascotaExistente = repositorioDeMascota.find(mascota.getId());
        if (mascotaExistente == null) {
            throw new EntityNotFoundException("Mascota no encontrada con ID: " + mascota.getId());
        }
        validarDuplicado(mascota);
        return repositorioDeMascota.save(mascota); // realiza merge de la mascota
    }

    public Mascota find(Long id) throws EntityNotFoundException{
        Mascota mascota = repositorioDeMascota.find(id);
        if (mascota == null) {
            throw new EntityNotFoundException("Mascota no encontrada con ID: " + id);
        }
        return mascota;
    }

    public List<Mascota> findMascotas(String criterio) throws EntityNotFoundException {
        return repositorioDeMascota.findWithLike(criterio.toLowerCase());
    }
    public List<Mascota> findMascotas() throws EntityNotFoundException {
        return repositorioDeMascota.findAll();
    }

    private void validarDuplicado(Mascota mascota) throws MascotaDuplicadaException {
        List<Mascota> posiblesDuplicadas = repositorioDeMascota.findByNombreAndPropietario(
                mascota.getNombre().toLowerCase(), mascota.getPropietario().getId()
        );
        for (Mascota existente : posiblesDuplicadas) {
            if (
                    mascota.getId() == null || !Objects.equals(mascota.getId(), existente.getId())
            ) {
                if (mascota.equals(existente)) {
                    throw new MascotaDuplicadaException("Ya existe una mascota registrada con esos datos para este cliente.");
                }
            }
        }
    }

}