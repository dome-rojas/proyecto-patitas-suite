package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.controladores.seguridad.SesionDeUsuario;
import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.FachadaDeMascota;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class EditarMascotaBean implements Serializable {
    private Mascota mascotaSeleccionada;

    @Inject
    private FachadaDeMascota fachadaDeMascota;
    @Named
    @Inject
    private SesionDeUsuario SesionDeUsuario;


    private List<Habitacion> habitacionesDisponibles;
    private List<Usuario> empleadosDisponibles;

    @Named
    @Inject
    private RegistrarMascotaBean registrarMascotaBean;

    @PostConstruct
    public void init() throws EntityNotFoundException {
        this.empleadosDisponibles = registrarMascotaBean.obtenerEmpleados();
        this.habitacionesDisponibles = registrarMascotaBean.obtenerHabitacionesDisponibles();
        this.mascotaSeleccionada = fachadaDeMascota.find(mascotaSeleccionada.getId());
        String mascotaId = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("mascotaId");
        if (mascotaId != null) {
            try {
                Long id = Long.parseLong(mascotaId);
                mascotaSeleccionada = fachadaDeMascota.find(id);
            } catch (EntityNotFoundException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mascota no encontrada", e.getMessage()));
            }
        }
    }

    public String actualizarMascota() {
        try {
            fachadaDeMascota.update(mascotaSeleccionada);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización exitosa", "Datos actualizados correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar", e.getMessage()));
        }
        return (SesionDeUsuario.getUsuario().getRol().getNombre().toLowerCase()+"-dashboard.xhtml?faces-redirect=true");
    }

    // Getters y Setters
    public Mascota getMascotaSeleccionada() {
        return mascotaSeleccionada;
    }

    public void setMascotaSeleccionada(Mascota mascotaSeleccionada) {
        this.mascotaSeleccionada = mascotaSeleccionada;
    }

    public List<Habitacion> getHabitacionesDisponibles() {
        return habitacionesDisponibles;
    }

    public void setHabitacionesDisponibles(List<Habitacion> habitacionesDisponibles) {
        this.habitacionesDisponibles = habitacionesDisponibles;
    }

    public List<Usuario> getEmpleadosDisponibles() {
        return empleadosDisponibles;
    }

    public void setEmpleadosDisponibles(List<Usuario> empleadosDisponibles) {
        this.empleadosDisponibles = empleadosDisponibles;
    }
}
