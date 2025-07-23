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
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class AdminBean implements Serializable {

    public int totalMascotas(){
        try{
            FachadaDeMascota fachada = new FachadaDeMascota();
            return fachada.findMascotas().size();
        }catch (EntityNotFoundException e) {
            FacesUtil.addErrorMessage("No se pudieron encontrar mascotas");
            return 0;
        }
    }

    public int totalHabitaciones(){

        FachadaDeHabitacion fachada = new FachadaDeHabitacion();
        return fachada.todasLasHabitaciones().size();

    }
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String editarUsuario(Usuario usuario){
        
        return null;
    }

    public int totalTareas(){
        try {
            FachadaDeTareas fachada = new FachadaDeTareas();
            return fachada.todasLasTareas().size();
        }catch (EntityNotFoundException e) {
            FacesUtil.addErrorMessage("No se pudieron encontrar tareas");
            return 0;
        }
    }

    public int totalEmpleados(){
        FachadaDeEmpleado fachada = new FachadaDeEmpleado();
        return fachada.obtenerTodosLosEmpleados().size();
    }
    public List<Usuario> todosEmpleados(){
        FachadaDeEmpleado fachada = new FachadaDeEmpleado();
        return fachada.obtenerTodosLosEmpleados();
    }

    public void totalHabitacionesDisponibles(){}

    public List<Usuario> listaUsuarios() {
        try {
            FachadaDeSeguridad fachada = new FachadaDeSeguridad();
            return fachada.findUsers();
        }catch (EntityNotFoundException e) {
                FacesUtil.addErrorMessage("No se pudo encontrar el usuarioS con id: ");
                return null;
            }
        }
    public List<Tarea> tareasRecientes() {
        try{
        FachadaDeTareas fachada = new FachadaDeTareas();
        return fachada.todasLasTareas();
        }catch (EntityNotFoundException e) {
            FacesUtil.addErrorMessage("No se pudieron encontrar tareas");
            return null;
        }
    }


    public String navegarARegistroEmpleado() {
        System.out.println("Navegando a la página de registro de empleado...");
        return "registrar-empleado.xhtml?faces-redirect=true";
    }

    public String navegarARegistroHabitacion() {
        System.out.println("Navegando a la página de registro de habitación...");
        return null;
    }

    public String generarReporte() {
        System.out.println("Generando reporte...");

        return null;
    }

    public String verHistorialMovimientos() {
        System.out.println("Viendo historial de movimientos...");
        return null;
    }

    public String cambiarCuidador(Mascota mascota) {
        System.out.println("Cambiando cuidador para: " + mascota.getNombre());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay cuidadores disponibles"));
        return null;
    }

    public String verFichaMascota(Mascota mascota) {
        System.out.println("Viendo ficha de mascota: " + mascota.getNombre());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay fichas para ver detalle"));
        return null;
    }

    public String asignarCuidador(Mascota mascota) {
        System.out.println("Asignando cuidador a: " + mascota.getNombre());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay Cuidadores disponibles"));
        return null;
    }

    public String verPerfilEmpleado(Usuario empleado) {
        System.out.println("Viendo perfil de empleado: " + empleado.getNombre());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay perfiles disponibles"));
        return null;
    }

    public String asignarTareaEmpleado(Usuario empleado) {
        System.out.println("Asignando tarea a empleado: " + empleado.getNombre());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay tareas para asignar"));
        return null;
    }

    public String verDetalleHabitacion(Habitacion habitacion) {
        System.out.println("Viendo detalle de habitación: " + habitacion.getId());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay Habitaciones para ver detalle"));
        return null;
    }

    public String asignarMascotaHabitacion(Habitacion habitacion) {
        System.out.println("Asignando mascota a habitación: " + habitacion.getId());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay habitaciones disponibles"));
        return null;
    }

    public String marcarHabitacionLista(Habitacion habitacion) {
        System.out.println("Marcando habitación como lista: " + habitacion.getId());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay habitaciones disponibles"));
        return null;
    }

}