package edu.unl.cc.patitas_suite.controladores.seguridad;

import edu.unl.cc.patitas_suite.controladores.AutenticacionBean;
import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.negocios.FachadaDeSeguridad;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Named
@RequestScoped
public class CambioDeCredencialesBean implements Serializable {
    private String contrasenaActual;
    @NotNull
    @Size(min=8, message="Contraseña muy corta")
    private String nuevaClave;
    @NotNull
    @Size(min=8, message="Contraseña muy corta")
    private String confirmarClave;

    @Inject
    private Usuario usuario;

    @Inject
    private FachadaDeSeguridad seguridad;
    private AutenticacionBean autenticacionBean;
    @PostConstruct
    public void init() {

        this.usuario = (Usuario) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuario");
        System.out.println("-------------"+usuario.getRol().getNombre().toLowerCase()+"--------------");
        System.out.println("EN EL INIT");

    }
    public String cambiarContrasena() throws ServletException {
        if (!nuevaClave.equals(confirmarClave)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las contraseñas no coinciden.", null));
            return null;
        }
        try {
            seguridad.updateContrasena(usuario.getId(), nuevaClave);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Contraseña actualizada correctamente.", null));

            System.out.println("-------------"+usuario.getRol().getNombre().toLowerCase()+"--------------");
            System.out.println("EN CAMBIO CONTRASEÑA");
            return usuario.getRol().getNombre().toLowerCase() + "-dashboard.xhtml?faces-redirect=true";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar la contraseña", null));
            return null;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Getters y Setters
    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public void setContrasenaActual(String contrasenaActual) {
        this.contrasenaActual = contrasenaActual;
    }

    public String getNuevaClave() {
        return nuevaClave;
    }

    public void setNuevaClave(String nuevaClave) {
        this.nuevaClave = nuevaClave;
    }

    public String getConfirmarClave() {
        return confirmarClave;
    }

    public void setConfirmarClave(String confirmarClave) {
        this.confirmarClave = confirmarClave;
    }
}
