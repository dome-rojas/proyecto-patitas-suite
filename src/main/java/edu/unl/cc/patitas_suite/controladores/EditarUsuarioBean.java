package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;
import edu.unl.cc.patitas_suite.negocios.FachadaDeEmpleado;
import edu.unl.cc.patitas_suite.negocios.FachadaDeSeguridad;
import edu.unl.cc.patitas_suite.utiles.GestorDeCifrado;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.context.Flash;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EditarUsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long usuarioId; // para identificar qué usuario editar
    private String nombreCompleto;
    private String username;
    private String correo;
    private String clave;
    private Long rolSeleccionadoId;

    private List<Rol> rolesDisponibles;

    @Inject
    private FachadaDeEmpleado fachadaDeEmpleado;

    @Inject
    private FachadaDeSeguridad fachadaDeSeguridad;

    @Inject
    private FacesContext facesContext;

    public EditarUsuarioBean() {
    }

    @PostConstruct
    public void init() {
        try {
            this.rolesDisponibles = fachadaDeSeguridad.findAllRoles();

            // Obtener el id enviado por Flash scope o parámetro
            Flash flash = facesContext.getExternalContext().getFlash();
            usuarioId = (Long) flash.get("usuarioId");

            if (usuarioId != null) {
                Usuario usuario = fachadaDeSeguridad.find(usuarioId);
                if (usuario != null) {
                    // Cargar datos en el formulario
                    this.username = usuario.getUserName();
                    this.nombreCompleto = usuario.getNombreCompleto();

                    //OJO
                    this.clave = GestorDeCifrado.decrypt(usuario.getClave());
                    this.correo = usuario.getCorreo();
                    if (usuario.getRol() != null) {
                        this.rolSeleccionadoId = usuario.getRol().getId();
                    }
                }
            } else {
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se recibió usuario para editar"));
            }
        } catch (Exception e) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar datos para editar"));
        }
    }

    public String actualizar() {
        try {
            Usuario usuario = fachadaDeSeguridad.find(usuarioId);
            if (usuario == null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario no existe"));
                return null;
            }

            // Actualiza los datos
            usuario.setUserName(username);
            usuario.setNombreCompleto(nombreCompleto);
            usuario.setClave(clave);
            usuario.setCorreo(correo);

            if (rolSeleccionadoId != null) {
                Rol rol = fachadaDeSeguridad.findRolId(rolSeleccionadoId);
                usuario.setRol(rol);
            }

            fachadaDeSeguridad.update(usuario);

            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Empleado actualizado correctamente."));

            // Puedes redirigir luego a donde quieras
            return "administrador-dashboard.xhtml?faces-redirect=true";
        } catch (Exception ex) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el empleado."));
            return null;
        }
    }

    // Getters y setters para JSF

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Long getRolSeleccionadoId() { return rolSeleccionadoId; }
    public void setRolSeleccionadoId(Long rolSeleccionadoId) { this.rolSeleccionadoId = rolSeleccionadoId; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public List<Rol> getRolesDisponibles() { return rolesDisponibles; }
    public void setRolesDisponibles(List<Rol> rolesDisponibles) { this.rolesDisponibles = rolesDisponibles; }
}
