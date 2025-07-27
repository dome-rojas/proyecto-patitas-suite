package edu.unl.cc.patitas_suite.utiles;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;
import edu.unl.cc.patitas_suite.negocios.FachadaDeEmpleado;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("usuarioConverter")
@RequestScoped
public class UsuarioConverter implements Converter<Usuario> {

    @Inject
    private FachadaDeEmpleado fachada;

    @Override
    public Usuario getAsObject(FacesContext context, UIComponent component, String value) {
        try{
            if (value == null || value.isEmpty()) return null;
            return fachada.obtenerEmpleado(Long.parseLong(value));
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Usuario value) {
        return value != null ? String.valueOf(value.getId()) : "";
    }
}
