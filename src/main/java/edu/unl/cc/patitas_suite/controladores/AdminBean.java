package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.faces.FacesUtil;
import edu.unl.cc.patitas_suite.negocios.*;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class AdminBean implements Serializable {
    @Inject
    private FachadaDeMascota fachadaDeMascota;
    @Inject
    private FachadaDeHabitacion fachadaDeHabitacion;
    @Inject
    private FachadaDeTareas fachadaDeTareas;
    @Inject
    private FachadaDeEmpleado fachadaDeEmpleado;
    @Inject
    private FachadaDeSeguridad fachadaDeSeguridad;
    @Inject
    private FachadaUsuarioMascotaTarea fachadaUsuarioMascotaTarea;
    @Inject
    private FacesContext facesContext;

    public String redirigirEdicion(Usuario usuario) {

        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuarioId", usuario.getId());
        return "editar-usuario?faces-redirect=true";
    }

    public int getTotalMascotas() {
        try {
            return fachadaDeMascota.obtenerTodas().size();
        } catch(Exception e) {
            FacesUtil.addErrorMessage("No se pudieron encontrar mascotas");
            return 0;
        }
    }

    public int getTotalHabitaciones() {
        try {
            return fachadaDeHabitacion.todasLasHabitaciones().size();
        } catch(Exception e) {
            FacesUtil.addErrorMessage("Error al contar habitaciones");
            return 0;
        }
    }

    public int getTotalTareas() {
        try {
            return fachadaDeTareas.todasLasTareas().size();
        } catch (EntityNotFoundException e) {
            FacesUtil.addErrorMessage("No se pudieron encontrar tareas");
            return 0;
        }
    }

    public int getTotalEmpleados() {
        try {
            return fachadaDeEmpleado.obtenerTodosLosEmpleados().size();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("No se pudieron contar empleados");
            return 0;
        }
    }

    // Listado de asignaciones pendientes si así lo requieres
    public int getTotalTareasAsignadasNoCompletadas() {
        try {
            return fachadaDeTareas.todasLasTareas().size();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("No se pudieron contar tareas pendientes");
            return 0;
        }
    }

    public List<Usuario> getListaUsuarios() {
        try {
            return fachadaDeSeguridad.findUsers();
        } catch (EntityNotFoundException e) {
            FacesUtil.addErrorMessage("No se pudieron encontrar usuarios");
            return new ArrayList<>();
        }
    }

    public List<Tarea> getTareasRecientes() {
        try {
            return fachadaDeTareas.todasLasTareas();
        } catch (EntityNotFoundException e) {
            FacesUtil.addErrorMessage("No se pudieron encontrar tareas");
            return new ArrayList<>();
        }
    }

    public List<Usuario> getTodosEmpleados() {
        try {
            return fachadaDeEmpleado.obtenerTodosLosEmpleados();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("No se pudieron recuperar empleados");
            return new ArrayList<>();
        }
    }

    public int getTotalHabitacionesDisponibles() {
        try {
            List<Habitacion> habitaciones = fachadaDeHabitacion.todasLasHabitaciones();
            return (int) habitaciones.stream()
                    .filter(h -> h.getEstado().name().equalsIgnoreCase("LIBRE"))
                    .count();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("No se pudieron calcular habitaciones disponibles");
            return 0;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String editarUsuario(Usuario usuario) {
        return null;
    }

    // Navegación y acciones - sin cambios mayores
    public String navegarARegistroEmpleado() {
        return "registrar-empleado.xhtml?faces-redirect=true";
    }

    public String navegarARegistroHabitacion() {
        return "registrar-habitacion.xhtml?faces-redirect=true";
    }

    public String generarReporte() {
        return null;
    }

    public String verHistorialMovimientos() {
        return null;
    }

    public String cambiarCuidador(Mascota mascota) {
        FacesUtil.addSuccessMessageAndKeep("No hay cuidadores disponibles");
        return null;
    }

    public String verFichaMascota(Mascota mascota) {
        FacesUtil.addSuccessMessageAndKeep("No hay fichas para ver detalle");
        return null;
    }

    public String asignarCuidador(Mascota mascota) {
        FacesUtil.addSuccessMessageAndKeep("No hay cuidadores disponibles");
        return null;
    }

    public String verPerfilEmpleado(Usuario empleado) {
        FacesUtil.addSuccessMessageAndKeep("No hay perfiles disponibles");
        return null;
    }

    public String asignarTareaEmpleado(Usuario empleado) {
        FacesUtil.addSuccessMessageAndKeep("No hay tareas para asignar");
        return null;
    }

    public String verDetalleHabitacion(Habitacion habitacion) {
        FacesUtil.addSuccessMessageAndKeep("No hay detalles para mostrar");
        return null;
    }

    public String asignarMascotaHabitacion(Habitacion habitacion) {
        FacesUtil.addSuccessMessageAndKeep("No hay habitaciones disponibles");
        return null;
    }

    public String marcarHabitacionLista(Habitacion habitacion) {
        FacesUtil.addSuccessMessageAndKeep("No hay habitaciones disponibles");
        return null;
    }
}