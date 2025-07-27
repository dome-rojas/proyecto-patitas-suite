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

    private String nombreCompleto;
    private String username;
    private String clave;
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
            nuevo.setUserName(username);
            nuevo.setCorreo(correo);
            nuevo.setNombreCompleto(nombreCompleto);
            nuevo.setPrimerIngreso(true);
            nuevo.setClave(clave); //
            nuevo.setRol(fachadaDeSeguridad.findRolId(rolSeleccionadoId));


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
        username = null;
        clave = null;
        telefono = null;
        correo = null;
        nombreCompleto =null;
        rolSeleccionadoId = null;
    }

    // Getters y Setters para JSF

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Long getRolSeleccionadoId() { return rolSeleccionadoId; }
    public void setRolSeleccionadoId(Long rolSeleccionadoId) { this.rolSeleccionadoId = rolSeleccionadoId; }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public List<Rol> getRolesDisponibles() { return rolesDisponibles; }
    public void setRolesDisponibles(List<Rol> rolesDisponibles) { this.rolesDisponibles = rolesDisponibles; }
}
