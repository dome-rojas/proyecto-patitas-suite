package edu.unl.cc.patitas_suite.controladores.seguridad;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.Rol;
import edu.unl.cc.patitas_suite.dominio.seguridad.TipoDeAccion;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.FachadaDeSeguridad;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@SessionScoped
public class SesionDeUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SesionDeUsuario.class.getName());

    @Inject
    FachadaDeSeguridad securityFacade;

    private Usuario usuario;

    public void postLogin(@NotNull Usuario usuario) throws EntityNotFoundException {
        logger.info("Usuario Registrado en : " + usuario.getNombre());
        this.usuario = usuario;
        //Rol rol = securityFacade.findRolesWithPermission(this.usuario.getId());
        //usuario.setRol(rol);
    }

    public boolean hasPermissionForPage(String pagePath) {
        return this.hasPermission(pagePath, TipoDeAccion.READ);
    }

    public boolean hasPermission(String resource, TipoDeAccion action) {
        return usuario.getRol().getPermisos().stream()
                .anyMatch(permission -> permission.matchWith(resource, action));
    }


    public boolean hasRole(@NotNull String roleName) {
        if (usuario == null || usuario.getRol() == null) {
            return false;
        }
        return usuario.getRol().getNombre().equals(roleName);
    }


    public Usuario getUsuario() {
        return usuario;
    }
}
