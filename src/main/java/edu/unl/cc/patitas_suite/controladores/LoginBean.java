package edu.unl.cc.patitas_suite.controladores;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
    private boolean recordar;

    public String login() {
        if ("admin".equals(username) && "admin123".equals(password)) {
            return "admin-dashboard?faces-redirect=true";
        } else if ("empleado1".equals(username) && "1234".equals(password)) {
            return "empleado-dashboard?faces-redirect=true";
        } else{
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error de autenticación",
                            "Credenciales incorrectas"));
            return null;
        }
    }
public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
}
    public void mostrarMensaje() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Error", "No hay reportes disponibles para el día de hoy"));
    }
    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isRecordar() { return recordar; }
    public void setRecordar(boolean recordar) { this.recordar = recordar; }

}