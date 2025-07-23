package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.faces.FacesUtil;
import edu.unl.cc.patitas_suite.negocios.FachadaDeMascota;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class MascotaBean implements Serializable {
    @Inject
    private FachadaDeMascota fachadaDeMascota;

    private Mascota mascotaSeleccionada;
    private List<Mascota> mascotas;

    @PostConstruct
    public void init() {
        try {
            mascotas = fachadaDeMascota.findMascotas("Estado");
        }catch (EntityNotFoundException e) {
            FacesUtil.addErrorMessage("No se pudieron encontrar mascotas");
        }

    }

    public void registrarMascota() {}
    public void editarMascota() {}
    public void asignarHabitacion(Long habitacionId) {}

    public FachadaDeMascota getFachadaDeMascota() {
        return fachadaDeMascota;
    }

    public void setFachadaDeMascota(FachadaDeMascota fachadaDeMascota) {
        this.fachadaDeMascota = fachadaDeMascota;
    }

    public Mascota getMascotaSeleccionada() {
        return mascotaSeleccionada;
    }

    public void setMascotaSeleccionada(Mascota mascotaSeleccionada) {
        this.mascotaSeleccionada = mascotaSeleccionada;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}
