package edu.unl.cc.patitas_suite.controladores.seguridad;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.dominio.seguridad.TipoDeAccion;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.security.Principal;

public class UsuarioPrincipal implements Principal, Serializable {

    private final Usuario usuario;

    public UsuarioPrincipal(@NotNull Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean hasPermissionForPage(String pagePath) {

        return this.hasPermission(pagePath, TipoDeAccion.READ);
    }

    public boolean hasPermission(String resource, TipoDeAccion action) {
        if (usuario == null || usuario.getRol() == null) {
            return false;
        }
        return usuario.getRol().getPermisos().stream()
                .anyMatch(permission -> permission.matchWith(resource, action));
    }

    @Override
    public String getName() {
        return usuario.getNombre();
    }
}
