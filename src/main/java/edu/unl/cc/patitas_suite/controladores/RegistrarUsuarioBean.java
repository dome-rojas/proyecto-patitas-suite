package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;
import edu.unl.cc.patitas_suite.negocios.FachadaDeEmpleado;
import edu.unl.cc.patitas_suite.negocios.FachadaDeSeguridad;
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
public class RegistrarUsuarioBean implements Serializable {

    private String apellido;
    private String nombre;
    private String cedula;
    private String telefono;
    private String correo;
    private Long rolSeleccionadoId;

    private List<Rol> rolesDisponibles;

    @Inject
    private FachadaDeEmpleado fachadaDeEmpleado;

    @Inject
    private FachadaDeSeguridad fachadaDeSeguridad;


    @PostConstruct
    public void init() {
        // Cargar lista de roles desde BD
        try {
            this.rolesDisponibles = fachadaDeSeguridad.findAllRoles();
        }catch(Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo encontrar Roles"));
        }

    }

    public String registrar() {
        try {
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setCorreo(correo);
            nuevo.setApellido(apellido);
            nuevo.setPrimerIngreso(true);
            nuevo.setClave("123456"); // ⚠️ Puedes generar clave temporal o pedirla
            nuevo.setRol(fachadaDeSeguridad.findRolId(rolSeleccionadoId));
            // Si en tu entidad tienes cédula y teléfono, agrégalas allí también

            fachadaDeSeguridad.create(nuevo);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Empleado registrado correctamente."));

            limpiarFormulario();
            return "administrador-dashboard.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar el empleado."));
            return null;
        }

    }

    private void limpiarFormulario() {
        nombre = null;
        cedula = null;
        telefono = null;
        correo = null;
        apellido=null;
        rolSeleccionadoId = null;
    }

    // Getters y Setters para JSF

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Long getRolSeleccionadoId() { return rolSeleccionadoId; }
    public void setRolSeleccionadoId(Long rolSeleccionadoId) { this.rolSeleccionadoId = rolSeleccionadoId; }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Rol> getRolesDisponibles() { return rolesDisponibles; }
    public void setRolesDisponibles(List<Rol> rolesDisponibles) { this.rolesDisponibles = rolesDisponibles; }
}
