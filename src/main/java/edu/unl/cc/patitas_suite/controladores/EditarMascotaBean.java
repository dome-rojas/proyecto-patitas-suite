package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.TipoDeTarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.FachadaDeMascota;
import edu.unl.cc.patitas_suite.negocios.FachadaDeTareas;
import edu.unl.cc.patitas_suite.negocios.FachadaUsuarioMascotaTarea;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class EditarMascotaBean implements Serializable {
    private Mascota mascotaSeleccionada;

    @Inject
    private FachadaDeMascota fachadaDeMascota;
    private UsuarioMascotaTarea usuarioMascotaTarea;
    private Usuario usuario;
    private Usuario empleadoActual;
    @Inject
    private FachadaUsuarioMascotaTarea fachadaUsuarioMascotaTarea;
@Inject
private FachadaDeTareas fachadaDeTareas;
    public List<UsuarioMascotaTarea> getAsignacionesMascotaPendientes() {
        if(mascotaSeleccionada != null) {
            return fachadaUsuarioMascotaTarea.obtenerAsignacionesPorMascota(mascotaSeleccionada.getId());
        }
        return List.of();
    }

    private List<Habitacion> habitacionesDisponibles;
    private List<Usuario> empleadosDisponibles;

    @Inject
    private RegistrarMascotaBean registrarMascotaBean;

    @PostConstruct
    public void init() {
        this.usuario = (Usuario) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuario");

        this.empleadosDisponibles = registrarMascotaBean.obtenerEmpleados();
        this.habitacionesDisponibles = registrarMascotaBean.obtenerHabitacionesDisponibles();

        String mascotaIdParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("mascotaId");

        if (mascotaIdParam != null && !mascotaIdParam.isEmpty()) {
            try {
                Long mascotaId = Long.parseLong(mascotaIdParam);
                this.mascotaSeleccionada = fachadaDeMascota.buscarPorId(mascotaId);
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
        Map<String, Object> params = new HashMap<>();
        params.put("mascotaId", mascotaSeleccionada.getId());
        List<UsuarioMascotaTarea> asignaciones = fachadaUsuarioMascotaTarea.obtenerAsignacionesPorMascota(mascotaSeleccionada.getId());
// Filtra o elige cuál usuario (empleado) está asignado como cuidador
        if (!asignaciones.isEmpty()) {
            empleadoActual = asignaciones.get(0).getUsuario(); // ejemplo: primer cuidador asignado
        } else {
            empleadoActual = null;
        }
    }


    public String actualizarMascota() {
        try {
            fachadaDeMascota.crearOActualizarMascota(mascotaSeleccionada);

            List<UsuarioMascotaTarea> asignacionesActuales = fachadaUsuarioMascotaTarea.obtenerAsignacionesPorMascota(mascotaSeleccionada.getId());

            Usuario empleadoAnterior = null;
            UsuarioMascotaTarea asignacionAnterior = null;

            if (asignacionesActuales != null && !asignacionesActuales.isEmpty()) {
                asignacionAnterior = asignacionesActuales.get(0);
                empleadoAnterior = asignacionAnterior.getUsuario();
            }

           if (empleadoActual != null) {
                Long nuevoEmpleadoId = empleadoActual.getId();
                Long anteriorEmpleadoId = empleadoAnterior != null ? empleadoAnterior.getId() : null;

                if (anteriorEmpleadoId == null || !anteriorEmpleadoId.equals(nuevoEmpleadoId)) {

                    if (asignacionAnterior != null) {

                        fachadaUsuarioMascotaTarea.eliminarAsignacion(asignacionAnterior.getId());

                    }

                    // Crear nueva asignación para el nuevo empleado/cuidador
                    UsuarioMascotaTarea nuevaAsignacion = new UsuarioMascotaTarea();
                    nuevaAsignacion.setUsuario(empleadoActual);
                    nuevaAsignacion.setMascota(mascotaSeleccionada);

                    try {
                        TipoDeTarea tipoCuidado = fachadaDeTareas.obtenerTipoDeTarea("ALIMENTAR");
                        if (tipoCuidado != null) {
                            Tarea tareaCuidador = new Tarea();
                            tareaCuidador.setTipo(tipoCuidado);
                            nuevaAsignacion.setTarea(tareaCuidador);
                        }
                    } catch (EntityNotFoundException e) {
                        // No existe tarea "cuidado", dejar null o manejar error
                        nuevaAsignacion.setTarea(null);
                    }

                    nuevaAsignacion.setFechaAsignacion(LocalDate.now());
                    nuevaAsignacion.setHora(LocalTime.now());
                    nuevaAsignacion.setCompletada(false);

                    fachadaUsuarioMascotaTarea.saveAsignacion(nuevaAsignacion);
                }
            }

            empleadoActual = empleadoActual;

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización exitosa", "Datos y asignaciones actualizadas correctamente"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar", e.getMessage()));
        }

        return usuario.getRol().getNombre().toLowerCase() + "-dashboard.xhtml?faces-redirect=true";
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

    public Usuario getEmpleadoActual() {
        return empleadoActual;
    }

    public void setEmpleadoActual(Usuario empleadoActual) {
        this.empleadoActual = empleadoActual;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
