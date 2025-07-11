package edu.unl.cc.patitas_suite.controladores;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class EmpleadoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private String cedula;
    // Propiedades para las tarjetas resumen del dashboard del empleado
    private String nombreEmpleado = "Juan Pérez"; // Simula el nombre del empleado loggeado
    private int mascotasACargo = 4;
    private int habitacionesAsignadas = 3;
    private int tareasPendientesHoy = 6;

    // Lista de mascotas asignadas al empleado, incluyendo sus tareas
    private List<MascotaAsignada> listaMascotasAsignadas;

    /**
     * Constructor de la clase EmpleadoBean.
     * Inicializa los datos de ejemplo para el dashboard del empleado.
     */
    public EmpleadoBean() {
        listaMascotasAsignadas = new ArrayList<>();
        // Datos de ejemplo para las mascotas asignadas del empleado
        listaMascotasAsignadas.add(new MascotaAsignada("Rocky", "101", "Activa",
                Arrays.asList(new Tarea("Alimentar", false), new Tarea("Pasear", false))));
        listaMascotasAsignadas.add(new MascotaAsignada("Pelusa", "102", "Activa",
                Arrays.asList(new Tarea("Limpiar habitación", false))));
        listaMascotasAsignadas.add(new MascotaAsignada("Max", "103", "Activa",
                Arrays.asList(new Tarea("Medicación", false), new Tarea("Aseo", false))));
    }
    public void mostrarMensaje() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Error", "No hay fichas disponibles"));
    }
    public String registrar() {
        if (cedula.isEmpty() || nombre.isEmpty()) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error de autenticación",
                            "Credenciales incorrectas"));
            return null;
        } else{
            return "admin-dashboard?faces-redirect=true";
        }
    }
    public String registrarMascota() {
            return "empleado-dashboard.xhtml?faces-redirect=true";
    }
    // --- Getters para las propiedades del resumen ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el nombre del empleado actualmente loggeado.
     * @return El nombre del empleado.
     */
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    /**
     * Obtiene el número total de mascotas a cargo del empleado.
     * @return Número de mascotas a cargo.
     */
    public int getMascotasACargo() {
        return mascotasACargo;
    }

    /**
     * Obtiene el número total de habitaciones asignadas al empleado.
     * @return Número de habitaciones asignadas.
     */
    public int getHabitacionesAsignadas() {
        return habitacionesAsignadas;
    }

    /**
     * Obtiene el número total de tareas pendientes para hoy.
     * @return Número de tareas pendientes.
     */
    public int getTareasPendientesHoy() {
        return tareasPendientesHoy;
    }

    // --- Getter para la lista de mascotas asignadas ---

    /**
     * Obtiene la lista de mascotas asignadas al empleado, incluyendo sus tareas.
     * @return Lista de objetos MascotaAsignada.
     */
    public List<MascotaAsignada> getListaMascotasAsignadas() {
        return listaMascotasAsignadas;
    }

    // --- Métodos de acción (simulados para la navegación) ---

    /**
     * Simula la navegación a la página de registro de una nueva mascota.
     * @return Una cadena que representa la regla de navegación (o null para quedarse en la misma página).
     */
    public String navegarARegistroMascota() {
        System.out.println("Navegando a la página de registro de nueva mascota...");
        return "registrar-mascota?faces-redirect=true";

    }

    /**
     * Simula la acción de ver la ficha completa de una mascota.
     * @param mascota El objeto MascotaAsignada cuya ficha se desea ver.
     * @return Una cadena que representa la regla de navegación (o null).
     */
    public String verFichaMascota(MascotaAsignada mascota) {
        System.out.println("Viendo ficha de mascota: " + mascota.getNombre());
        // Lógica para mostrar la ficha completa de la mascota, quizás pasando el ID de la mascota a otra vista
        return null;
    }

    // --- Clases internas para representar Mascota y Tarea ---

    /**
     * Clase interna estática que representa una mascota asignada a un empleado.
     * Implementa Serializable para ser compatible con JSF.
     */
    public static class MascotaAsignada implements Serializable {
        private String nombre;
        private String habitacion;
        private String estado;
        private List<Tarea> tareasDelDia;

        /**
         * Constructor de MascotaAsignada.
         * @param nombre El nombre de la mascota.
         * @param habitacion La habitación asignada a la mascota.
         * @param estado El estado actual de la mascota.
         * @param tareasDelDia Una lista de tareas pendientes para la mascota hoy.
         */
        public MascotaAsignada(String nombre, String habitacion, String estado, List<Tarea> tareasDelDia) {
            this.nombre = nombre;
            this.habitacion = habitacion;
            this.estado = estado;
            this.tareasDelDia = tareasDelDia;
        }

        // Getters para las propiedades de MascotaAsignada
        public String getNombre() { return nombre; }
        public String getHabitacion() { return habitacion; }
        public String getEstado() { return estado; }
        public List<Tarea> getTareasDelDia() { return tareasDelDia; }

        // Setters (opcionales, solo si los datos se van a modificar directamente desde la vista)
        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setHabitacion(String habitacion) { this.habitacion = habitacion; }
        public void setEstado(String estado) { this.estado = estado; }
        public void setTareasDelDia(List<Tarea> tareasDelDia) { this.tareasDelDia = tareasDelDia; }
    }

    /**
     * Clase interna estática que representa una tarea asociada a una mascota.
     * Implementa Serializable para ser compatible con JSF.
     */
    public static class Tarea implements Serializable {
        private String descripcion;
        private boolean completada; // Para el checkbox en la interfaz

        /**
         * Constructor de Tarea.
         * @param descripcion La descripción de la tarea.
         * @param completada El estado de la tarea (true si está completada, false si no).
         */
        public Tarea(String descripcion, boolean completada) {
            this.descripcion = descripcion;
            this.completada = completada;
        }

        // Getters para las propiedades de Tarea
        public String getDescripcion() { return descripcion; }
        public boolean isCompletada() { return completada; } // Usar 'is' para booleanos en JSF

        // Setters
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public void setCompletada(boolean completada) { this.completada = completada; }
    }
}
