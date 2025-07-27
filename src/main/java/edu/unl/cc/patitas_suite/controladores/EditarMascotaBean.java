package edu.unl.cc.patitas_suite.controladores;

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

    private Usuario usuario;


    private List<Habitacion> habitacionesDisponibles;
    private List<Usuario> empleadosDisponibles;

    @Inject
    private RegistrarMascotaBean registrarMascotaBean;

    @PostConstruct
    public void init() {
        // 1. Obtener el usuario desde la sesión
        this.usuario = (Usuario) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuario");

        // 2. Cargar empleados y habitaciones desde el bean inyectado
        this.empleadosDisponibles = registrarMascotaBean.obtenerEmpleados();
        this.habitacionesDisponibles = registrarMascotaBean.obtenerHabitacionesDisponibles();

        // 3. Obtener el ID de mascota desde los parámetros de la URL (GET)
        String mascotaIdParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("mascotaId");

        if (mascotaIdParam != null && !mascotaIdParam.isEmpty()) {
            try {
                Long mascotaId = Long.parseLong(mascotaIdParam);
                this.mascotaSeleccionada = fachadaDeMascota.find(mascotaId);
            } catch (NumberFormatException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ID inválido", "El ID de la mascota no es válido."));
            } catch (EntityNotFoundException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mascota no encontrada", e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Mascota no seleccionada", "No se especificó un ID de mascota."));
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
        return (usuario.getRol().getNombre().toLowerCase()+"-dashboard.xhtml?faces-redirect=true");
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
