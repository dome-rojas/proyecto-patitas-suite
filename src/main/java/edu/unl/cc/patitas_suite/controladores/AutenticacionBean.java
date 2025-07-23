package edu.unl.cc.patitas_suite.controladores;

import edu.unl.cc.patitas_suite.controladores.seguridad.SesionDeUsuario;
import edu.unl.cc.patitas_suite.controladores.seguridad.UsuarioPrincipal;
import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.faces.FacesUtil;
import edu.unl.cc.patitas_suite.negocios.FachadaDeSeguridad;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.logging.Logger;

@Named
@RequestScoped
public class AutenticacionBean {
    private static Logger logger = Logger.getLogger(AutenticacionBean.class.getName());

    @NotNull
    private String nombreUsuario;
    @NotNull
    @Size(min=8, message="Contraseña muy corta")
    private String clave;

    @Inject
    private FachadaDeSeguridad securityFacade;

    @Inject
    private SesionDeUsuario sesionDeUsuario;

    //@Inject
    //private FacesContext facesContext;

    public String login(){
        logger.info("Logging in with username: " + nombreUsuario);
        logger.info("Logging in with password: " + clave);
        try {
            Usuario usuario = securityFacade.authenticate(nombreUsuario, clave);
            setHttpSession(usuario);

            FacesUtil.addMessageAndKeep(FacesMessage.SEVERITY_INFO, "Aviso", "Bienvenido " + usuario.getNombre() + " a la aplicación Patitas Suite.");
            //FacesMessage fc = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", "Bienvenido " + user.getName() + " a la aplicación Jbrew.");
            //facesContext.addMessage(null, fc);
            //facesContext.getExternalContext().getFlash().setKeepMessages(true);

            System.out.println("--------------> userSession Login: " + sesionDeUsuario.getUsuario());
            sesionDeUsuario.postLogin(usuario);
            if(usuario.esPrimerIngreso()){
                usuario.setPrimerIngreso(false);
                FacesUtil.addSuccessMessageAndKeep("Bienvenido al sistema. Por favor, actualice su información.");
                return ("cambiar-contraseña.xhtml?faces-redirect=true");
            }
            return (usuario.getRol().toString().toLowerCase()+"-dashboard.xhtml?faces-redirect=true");
        } catch (Exception e) {
            FacesUtil.addMessage(FacesMessage.SEVERITY_ERROR,  e.getMessage(), null);
            return null;
        }
    }

    public String logout() throws ServletException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        FacesUtil.addSuccessMessageAndKeep("Desconectado con éxito.");

        ((jakarta.servlet.http.HttpServletRequest) facesContext.getExternalContext().getRequest()).logout();
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean verifyUserSession(){
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getSessionMap().containsKey("user");
    }

    /**
     * Establece la sessión de usuario
     * @param usuario
     */
    private void setHttpSession(Usuario usuario){
        FacesContext context = FacesContext.getCurrentInstance();
        UsuarioPrincipal userPrincipal = new UsuarioPrincipal(usuario);
        context.getExternalContext().getSessionMap().put("usuario", userPrincipal);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
