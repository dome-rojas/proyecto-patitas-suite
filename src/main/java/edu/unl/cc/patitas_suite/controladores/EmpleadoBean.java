package edu.unl.cc.patitas_suite.controladores;


import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.EstadoHabitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.faces.FacesUtil;
import edu.unl.cc.patitas_suite.negocios.FachadaDeEmpleado;
import edu.unl.cc.patitas_suite.negocios.FachadaDeTareas;
import edu.unl.cc.patitas_suite.negocios.FachadaUsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.negocios.servicios.RepositorioDeHabitaciones;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class EmpleadoBean implements Serializable {

    private List<Mascota> listaMascotas;
    private List<UsuarioMascotaTarea> listaAsignacionesTareas;
    private List<Habitacion> listaHabitaciones;
    @Inject
    private RepositorioDeHabitaciones rp;

    @Inject
    private FachadaDeEmpleado fachadaEmpleado;

    @Inject
    private FachadaUsuarioMascotaTarea fachadaUsuarioMascotaTarea;

    @Inject
    private FachadaDeTareas fachadaTareas;

    private Usuario empleado;


    @PostConstruct
    public void init() {
        this.empleado = (Usuario) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuario");
        try {
            if (empleado != null) {
                this.listaMascotas = fachadaEmpleado.obtenerMascotasAsignadas(empleado.getId());
                this.listaAsignacionesTareas = fachadaUsuarioMascotaTarea.obtenerAsignacionesPorUsuario(empleado.getId());
                this.listaHabitaciones = fachadaEmpleado.obtenerHabitacionesAsignadas(empleado.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UsuarioMascotaTarea> getListaTareas(){
        return listaAsignacionesTareas;
}
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarHabitacionLimpieza(Habitacion habitacion){
        habitacion.setEstado(EstadoHabitacion.LIMPIEZA);
        rp.save(habitacion);
        FacesUtil.agregarInfo("Estado Actualizado a Limpieza");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarComoCompletado(UsuarioMascotaTarea tarea) {
        try {
            fachadaUsuarioMascotaTarea.marcarComoCompletada(tarea.getId()); // llama el facade y actualiza en BD
            FacesUtil.agregarInfo("Tarea marcada como completada");
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Error", "No se pudo marcar la tarea como completada");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarHabitacionLista(Habitacion habitacion) {
        habitacion.setEstado(EstadoHabitacion.LIBRE);
        rp.save(habitacion);
        FacesUtil.agregarInfo("Estado Actualizado a Libre");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarAsignacionTareaComoCompletada(Long asignacionId) throws Exception {
        fachadaUsuarioMascotaTarea.marcarComoCompletada(asignacionId);
        FacesUtil.agregarInfo("Tarea actualizada correctamente");
    }
        @TransactionAttribute(TransactionAttributeType.REQUIRED)
        public void marcarDetalleTarea(UsuarioMascotaTarea tarea) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Detalle tarea",
                            tarea.getTarea().getTipo().getDescripcion() // o el atributo que quieras mostrar
                    )
            );
        }


    public String irRegistrarMascota() {
        return "registrar-mascota.xhtml?faces-redirect=true";
    }

    public String editarMascota(Mascota mascota) {
        return "actualizar-mascota.xhtml?faces-redirect=true&mascotaId="+mascota.getId();
    }

    // Getters y Setters

    public List<Mascota> getListaMascotas() {
        return listaMascotas;
    }

    public void setListaMascotas(List<Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    public void setListaAsignacionesTareas(List<UsuarioMascotaTarea> listaAsignacionesTareas) {
        this.listaAsignacionesTareas = listaAsignacionesTareas;
    }

    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = listaHabitaciones;
    }

    public Usuario getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }
    public List<UsuarioMascotaTarea> getListaAsignacionesTareas() {
        return listaAsignacionesTareas;
    }
}
