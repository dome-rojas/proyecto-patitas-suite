package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Cliente;
import edu.unl.cc.patitas_suite.dominio.seguridad.Complexion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Habitacion;
import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.faces.FacesUtil;
import edu.unl.cc.patitas_suite.negocios.FachadaDeCliente;
import edu.unl.cc.patitas_suite.negocios.FachadaDeEmpleado;
import edu.unl.cc.patitas_suite.negocios.FachadaDeHabitacion;
import edu.unl.cc.patitas_suite.negocios.FachadaDeMascota;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class RegistrarMascotaBean implements Serializable {
    private static Logger logger = Logger.getLogger(AutenticacionBean.class.getName());

    // --- Datos de la mascota ---
    private String nombreMascota;
    private String especie;
    private LocalDate edad;
    private Float peso;
    private Long habitacionSeleccionadaID;
    private String habitacion;
    private Long empleadoAsignadoID;

    // --- Datos del cliente ---
    private String nombreCliente;
    private String telefono;
    private String direccion;
    private String metodoPago;

    @Inject
    private FachadaDeCliente fachadaCliente;

    @Inject
    private FachadaDeMascota fachadaMascota;

    @Inject
    private FachadaDeEmpleado fachadaEmpleado;

    @Inject
    private FachadaDeHabitacion fachadaHabitacion;
    private Usuario usuario;
    private String raza;
    private Complexion complexion;
    @PostConstruct
    public void init() {
        this.usuario = (Usuario) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuario");
    }
    public List<Complexion> getListaComplexiones() {
        return Arrays.asList(Complexion.values());
    }

    public String registrarMascota() {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(nombreCliente);
            cliente.setContacto(telefono);
            cliente.setDireccion(direccion);

            fachadaCliente.guardar(cliente);

            Mascota mascota = new Mascota();
            mascota.setNombre(nombreMascota);
            mascota.setEspecie(especie);
            mascota.setFechaNacimiento(edad);
            mascota.setPeso(15.6f);
            mascota.setRaza(raza);
            mascota.setComplexion(complexion);
            mascota.setHabitacion(fachadaHabitacion.buscarPorId(habitacionSeleccionadaID));
            mascota.setCuidador(fachadaEmpleado.obtenerEmpleado(empleadoAsignadoID));
            mascota.setPropietario(cliente);

            Mascota mascotaGuardada = fachadaMascota.create(mascota);
            System.out.println("Mascota creada con ID: " + mascotaGuardada.getId());

            fachadaEmpleado.asignarMascotaAEmpleado(mascotaGuardada.getId(), empleadoAsignadoID);

            FacesUtil.addSuccessMessageAndKeep("Registro exitoso", "Mascota y propietario guardados correctamente.");
            limpiarFormulario();

        } catch (Exception e) {
            e.printStackTrace(); // necesario
            FacesUtil.addErrorMessage("Error al registrar la mascota", e.getMessage());
        }

        return (usuario.getRol().getNombre().toLowerCase()+"-dashboard.xhtml?faces-redirect=true");
    }

    public List<Habitacion> obtenerHabitacionesDisponibles(){
        return fachadaHabitacion.habitacionesDisponibles();
    }
    public List<Usuario> obtenerEmpleados(){
        return fachadaEmpleado.obtenerTodosLosEmpleados();
    }
    public void limpiarFormulario() {
        nombreMascota = null;
        especie = null;
        edad = null;
        peso = null;
        habitacionSeleccionadaID = null;
        empleadoAsignadoID = null;

        nombreCliente = null;
        telefono = null;
        direccion = null;
        metodoPago = null;
    }

    // --- Getters y Setters (obligatorios para JSF) ---

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Complexion getComplexion() {
        return complexion;
    }

    public void setComplexion(Complexion complexion) {
        this.complexion = complexion;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getEdad() {
        return edad;
    }

    public void setEdad(LocalDate edad) {
        this.edad = edad;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Long getHabitacionSeleccionadaID() {
        return habitacionSeleccionadaID;
    }

    public void setHabitacionSeleccionadaID(Long habitacionSeleccionadaID) {
        this.habitacionSeleccionadaID = habitacionSeleccionadaID;
    }

    public Long getEmpleadoAsignadoID() {
        return empleadoAsignadoID;
    }

    public void setEmpleadoAsignadoID(Long empleadoAsignadoID) {
        this.empleadoAsignadoID = empleadoAsignadoID;
    }

    public String getNombreMascota() { return nombreMascota; }
    public void setNombreMascota(String nombreMascota) { this.nombreMascota = nombreMascota; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
