package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.controladores.seguridad.SesionDeUsuario;
import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.comun.UsuarioMascotaTarea;
import edu.unl.cc.patitas_suite.dominio.seguridad.*;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.faces.FacesUtil;
import edu.unl.cc.patitas_suite.negocios.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class RegistrarMascotaBean implements Serializable {
    private static Logger logger = Logger.getLogger(AutenticacionBean.class.getName());

    // --- Datos de la mascota ---
    private Long mascotaSeleccionadaID;
    private Mascota mascotaSeleccionada;
    private Long clienteSeleccionadaID;
    private Cliente clienteSeleccionado;
    private String nombreMascota;
    private String especie;
    private LocalDate edad;
    private float peso=0;
    private Long habitacionSeleccionadaID;
    private String habitacion;
    private Long empleadoAsignadoID;
    private List<Mascota> mascotas;
    private List<Cliente> propietarios;
    // --- Datos del cliente ---
    private String nombreCliente;
    private String telefono;
    private String direccion;
    private String metodoPago;

    private List<String> opcionesCheckbox; // Lista que alimenta los checkboxes

    private List<String> seleccionCheckbox;
    @Inject
    private FachadaDeCliente fachadaCliente;

    @Inject
    private FachadaDeMascota fachadaMascota;

    @Inject
    private FachadaDeEmpleado fachadaEmpleado;

    @Inject
    private SesionDeUsuario sesionDeUsuario;

    @Inject
    private FachadaUsuarioMascotaTarea usrmsF;

    @Inject
    private FachadaDeHabitacion fachadaHabitacion;
    @Inject
    private FachadaDeTareas fachadaTarea;

    private Usuario usuario;
    private String raza;
    private Complexion complexion;
    @PostConstruct
    public void init() {
        Object value = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuario");
        opcionesCheckbox = cargarOpcionesDinamicas();
        if (value != null && value instanceof Usuario usuarioSesion) {
            this.usuario = usuarioSesion;
        } else {
            System.err.println("‼ Usuario no encontrado en sesión");
        }
    }


    public List<Complexion> getListaComplexiones() {
        return Arrays.asList(Complexion.values());
    }

    public List<String> cargarOpcionesDinamicas() {
        try {
            List<TipoDeTarea> tipos = fachadaTarea.obtenerTiposDeTarea();
            // Mapear la lista de TipoDeTarea a lista de Strings con sus nombres
            return tipos.stream()
                    .map(TipoDeTarea::getNombre)
                    .toList();
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Error al cargar tipos de tarea", e);
        }
    }
    public void cargarDatosCliente(){
        if(clienteSeleccionadaID!=null){
            try{
                clienteSeleccionado=fachadaCliente.buscarPorId(clienteSeleccionadaID);
                if(clienteSeleccionado!=null){
                    nombreCliente=clienteSeleccionado.getNombre();
                    telefono=clienteSeleccionado.getContacto();
                    direccion=clienteSeleccionado.getDireccion();
                }
            }catch (EntityNotFoundException e) {
                FacesUtil.addErrorMessage("Cliente no encontrada", e.getMessage());
            }
        }
    }
    public void cargarDatosMascota() {
        if (mascotaSeleccionadaID != null) {
            try {
                mascotaSeleccionada = fachadaMascota.buscarPorId(mascotaSeleccionadaID);
                if (mascotaSeleccionada != null) {
                    // Rellena todos los campos para mostrar en los inputs
                    nombreMascota = mascotaSeleccionada.getNombre();
                    especie = mascotaSeleccionada.getEspecie();
                    raza = mascotaSeleccionada.getRaza();
                    edad = mascotaSeleccionada.getFechaNacimiento();
                    peso = mascotaSeleccionada.getPeso();
                    complexion = mascotaSeleccionada.getComplexion();
                }
            } catch (EntityNotFoundException e) {
                FacesUtil.addErrorMessage("Mascota no encontrada", e.getMessage());
            }
        }
    }


    public String registrarMascota() {
        if (usuario == null) {
            Object value = FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .get("usuario");
            if (value != null && value instanceof Usuario usuarioSesion) {
                usuario = usuarioSesion;
            }
            if (usuario == null) {
                FacesUtil.addErrorMessage("Sesión perdida", "Por favor, inicia sesión nuevamente.");
                return "/login.xhtml?faces-redirect=true";
            }
        }

        try {
            // Refuerza por si la sesión no tenía usuario
            if (usuario == null) {
                usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            }

            // *** CLIENTE ***
            Cliente cliente = fachadaCliente.buscarPorId(clienteSeleccionadaID);
            if (cliente == null) {
                cliente = new Cliente();
                cliente.setNombre(nombreCliente);
                cliente.setContacto(telefono);
                cliente.setDireccion(direccion);
                fachadaCliente.guardar(cliente);
            }

            // *** MASCOTA ***
            Mascota mascota;
            if (mascotaSeleccionadaID != null) {
                try {
                    mascota = fachadaMascota.buscarPorId(mascotaSeleccionadaID);
                    // Actualiza datos
                    mascota.setNombre(nombreMascota);
                    mascota.setEspecie(especie);
                    mascota.setFechaNacimiento(edad);
                    mascota.setPeso(peso);
                    mascota.setRaza(raza);
                    mascota.setComplexion(complexion);
                    mascota.setHabitacion(fachadaHabitacion.buscarPorId(habitacionSeleccionadaID));
                    mascota.setPropietario(cliente);
                    mascota = fachadaMascota.crearOActualizarMascota(mascota); // método para update o create
                } catch (EntityNotFoundException e) {
                    // No existe mascota, la creamos abajo
                    mascota = null;
                }
            } else {
                mascota = null;
            }

            if (mascota == null) {
                mascota = new Mascota();
                mascota.setNombre(nombreMascota);
                mascota.setEspecie(especie);
                mascota.setFechaNacimiento(edad);
                mascota.setPeso(peso);
                mascota.setRaza(raza);
                mascota.setComplexion(complexion);
                mascota.setHabitacion(fachadaHabitacion.buscarPorId(habitacionSeleccionadaID));
                mascota.getHabitacion().setEstado(EstadoHabitacion.OCUPADA);
                mascota.setPropietario(cliente);
                mascota = fachadaMascota.crearOActualizarMascota(mascota);
            }

            // *** ASIGNACIONES DE TAREAS ***
            if (usuario.getAsignaciones() == null) {
                usuario.setAsignaciones(new HashSet<>());
            }

            for(String tareaNombre : seleccionCheckbox) {
                TipoDeTarea tipo = fachadaTarea.obtenerTipoDeTarea(tareaNombre);
                Tarea tareaNueva = new Tarea();
                tareaNueva.setTipo(tipo);
                // Persistir la tarea ANTES de la asignación
                tareaNueva = fachadaTarea.crearTarea(tareaNueva);

                // Crear asignación
                UsuarioMascotaTarea asignacion = new UsuarioMascotaTarea();
                asignacion.setTarea(tareaNueva);
                asignacion.setUsuario(usuario);
                asignacion.setMascota(mascota);
                asignacion.setFechaAsignacion(LocalDate.now());
                asignacion.setHora(LocalTime.now());
                asignacion.setCompletada(false);

                asignacion = usrmsF.saveAsignacion(asignacion);
                usuario.getAsignaciones().add(asignacion);
            }
            fachadaEmpleado.guardarEmpleado(usuario);

            FacesUtil.addSuccessMessage("Registro exitoso", "Mascota y asignaciones registradas correctamente.");

            limpiarFormulario();

            return usuario.getRol().getNombre().toLowerCase() + "-dashboard.xhtml?faces-redirect=true";

        } catch (Exception e) {
            FacesUtil.addErrorMessage("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public List<String> getOpcionesCheckbox() {
        return opcionesCheckbox;
    }

    public Long getClienteSeleccionadaID() {
        return clienteSeleccionadaID;
    }

    public void setClienteSeleccionadaID(Long clienteSeleccionadaID) {
        this.clienteSeleccionadaID = clienteSeleccionadaID;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Cliente clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public void setOpcionesCheckbox(List<String> opcionesCheckbox) {
        this.opcionesCheckbox = opcionesCheckbox;
    }

    public List<String> getSeleccionCheckbox() {
        return seleccionCheckbox;
    }

    public void setSeleccionCheckbox(List<String> seleccionCheckbox) {
        this.seleccionCheckbox = seleccionCheckbox;
    }
    public List<Habitacion> obtenerHabitacionesDisponibles(){
        return fachadaHabitacion.habitacionesDisponibles();
    }
    public List<Usuario> obtenerEmpleados(){
        return fachadaEmpleado.obtenerTodosLosEmpleados();
    }

    public Mascota getMascotaSeleccionada() {
        return mascotaSeleccionada;
    }

    public void setMascotaSeleccionada(Mascota mascotaSeleccionada) {
        this.mascotaSeleccionada = mascotaSeleccionada;
    }

    public List<Mascota> getMascotas() {
        return fachadaMascota.obtenerTodas();
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public List<Cliente> getPropietarios() {
        return fachadaCliente.obtenerTodos();
    }

    public void setPropietarios(List<Cliente> propietarios) {
        this.propietarios = propietarios;
    }

    public void limpiarFormulario() {
        nombreMascota = null;
        especie = null;
        edad = null;
        peso = 0;
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

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
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

    public Long getMascotaSeleccionadaID() {
        return mascotaSeleccionadaID;
    }

    public void setMascotaSeleccionadaID(Long mascotaSeleccionadaID) {
        this.mascotaSeleccionadaID = mascotaSeleccionadaID;
    }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
