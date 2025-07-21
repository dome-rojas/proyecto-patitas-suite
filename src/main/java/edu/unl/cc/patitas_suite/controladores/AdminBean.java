package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Tarea;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.*;
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

    private static final long serialVersionUID = 1L;

    private int totalMascotasHospedadas = 15;
    private int habitacionesDisponibles = 5;
    private int totalHabitaciones = 20;
    private int empleadosActivos = 7;
    private int tareasEnCurso = 3;

    private List<Mascota> listaMascotasHospedadas;
    private List<Empleado> listaEmpleados;
    private List<Habitacion> listaHabitaciones;

    public AdminBean() {

        listaMascotasHospedadas = new ArrayList<>();
        listaMascotasHospedadas.add(new Mascota("Rocky", "101", "Juan Pérez", "Activa"));
        listaMascotasHospedadas.add(new Mascota("Pelusa", "102", "Andrea Gómez", "Activa"));
        listaMascotasHospedadas.add(new Mascota("Max", "103", "- (no asignado)", "Activa"));
        listaMascotasHospedadas.add(new Mascota("Luna", "104", "Carlos Torres", "Activa"));

        listaEmpleados = new ArrayList<>();
        listaEmpleados.add(new Empleado("Juan Pérez", 4, 3));
        listaEmpleados.add(new Empleado("Andrea Gómez", 3, 2));
        listaEmpleados.add(new Empleado("Carlos Torres", 0, 0));
        listaEmpleados.add(new Empleado("María López", 2, 1));

        listaHabitaciones = new ArrayList<>();
        listaHabitaciones.add(new Habitacion("101", "Ocupada", "Rocky"));
        listaHabitaciones.add(new Habitacion("102", "Ocupada", "Pelusa"));
        listaHabitaciones.add(new Habitacion("103", "Ocupada", "Max"));
        listaHabitaciones.add(new Habitacion("104", "Ocupada", "Luna"));
        listaHabitaciones.add(new Habitacion("105", "Disponible", "-"));
        listaHabitaciones.add(new Habitacion("106", "Limpieza", "-"));
        listaHabitaciones.add(new Habitacion("107", "Disponible", "-"));
    }
    public int totalMascotas() throws EntityNotFoundException {
        FachadaDeMascota fachada = new FachadaDeMascota();
        return fachada.findMascotas().size();
    }

    public int totalHabitaciones() throws EntityNotFoundException {
        FachadaDeHabitacion fachada = new FachadaDeHabitacion();
        return fachada.todasLasHabitaciones().size();
    }

    public int totalTareas() throws EntityNotFoundException {
        FachadaDeTareas fachada = new FachadaDeTareas();
        return fachada.todasLasTareas().size();
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

    public List<Usuario> listaUsuarios() throws EntityNotFoundException {
        FachadaDeSeguridad fachada = new FachadaDeSeguridad();
        return fachada.findUsers();
    }
    public List<Tarea> tareasRecientes() throws EntityNotFoundException {
        FachadaDeTareas fachada = new FachadaDeTareas();
        return fachada.todasLasTareas();
    }


    public int getTotalMascotasHospedadas() {
        return totalMascotasHospedadas;
    }

    public int getHabitacionesDisponibles() {
        return habitacionesDisponibles;
    }


    public int getEmpleadosActivos() {
        return empleadosActivos;
    }

    public int getTareasEnCurso() {
        return tareasEnCurso;
    }

    public List<Mascota> getListaMascotasHospedadas() {
        return listaMascotasHospedadas;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
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

    public String verPerfilEmpleado(Empleado empleado) {
        System.out.println("Viendo perfil de empleado: " + empleado.getNombre());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay perfiles disponibles"));
        return null;
    }

    public String asignarTareaEmpleado(Empleado empleado) {
        System.out.println("Asignando tarea a empleado: " + empleado.getNombre());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay tareas para asignar"));
        return null;
    }

    public String verDetalleHabitacion(Habitacion habitacion) {
        System.out.println("Viendo detalle de habitación: " + habitacion.getNumero());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay Habitaciones para ver detalle"));
        return null;
    }

    public String asignarMascotaHabitacion(Habitacion habitacion) {
        System.out.println("Asignando mascota a habitación: " + habitacion.getNumero());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay habitaciones disponibles"));
        return null;
    }

    public String marcarHabitacionLista(Habitacion habitacion) {
        System.out.println("Marcando habitación como lista: " + habitacion.getNumero());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay habitaciones disponibles"));
        return null;
    }

    public static class Mascota implements Serializable {
        private String nombre;
        private String habitacion;
        private String cuidadorAsignado;
        private String estado;

        public Mascota(String nombre, String habitacion, String cuidadorAsignado, String estado) {
            this.nombre = nombre;
            this.habitacion = habitacion;
            this.cuidadorAsignado = cuidadorAsignado;
            this.estado = estado;
        }

        // Getters para que JSF pueda acceder a las propiedades de la Mascota.
        public String getNombre() { return nombre; }
        public String getHabitacion() { return habitacion; }
        public String getCuidadorAsignado() { return cuidadorAsignado; }
        public String getEstado() { return estado; }

        // Setters (opcionales si los datos son solo de lectura en la vista).
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setHabitacion(String habitacion) { this.habitacion = habitacion; }
        public void setCuidadorAsignado(String cuidadorAsignado) { this.cuidadorAsignado = cuidadorAsignado; }
        public void setEstado(String estado) { this.estado = estado; }
    }

    public static class Empleado implements Serializable {
        private String nombre;
        private int numMascotasAsignadas;
        private int habitacionesACargo;

        public Empleado(String nombre, int numMascotasAsignadas, int habitacionesACargo) {
            this.nombre = nombre;
            this.numMascotasAsignadas = numMascotasAsignadas;
            this.habitacionesACargo = habitacionesACargo;
        }

        // Getters para Empleado.
        public String getNombre() { return nombre; }
        public int getNumMascotasAsignadas() { return numMascotasAsignadas; }
        public int getHabitacionesACargo() { return habitacionesACargo; }

        // Setters para Empleado.
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setNumMascotasAsignadas(int numMascotasAsignadas) { this.numMascotasAsignadas = numMascotasAsignadas; }
        public void setHabitacionesACargo(int habitacionesACargo) { this.habitacionesACargo = habitacionesACargo; }
    }

    public static class Habitacion implements Serializable {
        private String numero;
        private String estado;
        private String ocupadaPor;

        public Habitacion(String numero, String estado, String ocupadaPor) {
            this.numero = numero;
            this.estado = estado;
            this.ocupadaPor = ocupadaPor;
        }

        // Getters para Habitacion.
        public String getNumero() { return numero; }
        public String getEstado() { return estado; }
        public String getOcupadaPor() { return ocupadaPor; }

        // Setters para Habitacion.
        public void setNumero(String numero) { this.numero = numero; }
        public void setEstado(String estado) { this.estado = estado; }
        public void setOcupadaPor(String ocupadaPor) { this.ocupadaPor = ocupadaPor; }
    }
}