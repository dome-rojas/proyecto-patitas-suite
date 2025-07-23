package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.controladores.seguridad.SesionDeUsuario;
import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.EstadoHabitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.faces.FacesUtil;
import edu.unl.cc.patitas_suite.negocios.FachadaDeEmpleado;
import edu.unl.cc.patitas_suite.negocios.FachadaDeTareas;
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
    private List<Tarea> listaTareas;
    private List<Habitacion> listaHabitaciones;
    @Inject
    private RepositorioDeHabitaciones rp;

    @Inject
    private FachadaDeEmpleado fachadaEmpleado;

    @Inject
    private FachadaDeTareas fachadaTareas;

    @Inject
    private SesionDeUsuario sesionDeUsuario;

    @PostConstruct
    public void init() throws Exception {
        Usuario empleado = sesionDeUsuario.getUsuario();

        if (empleado != null) {
            this.listaMascotas = fachadaEmpleado.obtenerMascotasDelEmpleado(empleado.getId());
            this.listaTareas = fachadaEmpleado.obtenerTareasPendientesDeEmpleado(empleado.getId());
            this.listaHabitaciones = fachadaEmpleado.habitacionesACargo(empleado.getId());
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarHabitacionLista(Habitacion habitacion) {
        habitacion.setEstado(EstadoHabitacion.LIBRE);
        rp.save(habitacion);
        FacesUtil.agregarInfo("Estado Actualizado a Libre");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarHabitacionLimpieza(Habitacion habitacion){
        habitacion.setEstado(EstadoHabitacion.LIMPIEZA);
        rp.save(habitacion);
        FacesUtil.agregarInfo("Estado Actualizado a Limpieza");
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarComoCompletado(Tarea tarea) throws Exception {
        tarea.setCompletada(true);
        fachadaTareas.actualizarTarea(fachadaTareas.buscarPorId(tarea.getId()));
        FacesUtil.agregarInfo("Tarea actualizada Correctamente");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void marcarDetalleTarea(Tarea tarea){
        FacesUtil.agregarInfo(tarea.getTipo().getDescripcion());
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

    public List<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(List<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }

    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = listaHabitaciones;
    }
}
