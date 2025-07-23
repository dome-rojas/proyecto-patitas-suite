package edu.unl.cc.patitas_suite.controladores.seguridad;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@RequestScoped
public class TemporalBean implements Serializable {
    public void mensajeDeError(){
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "AVISO!", "Opcion No Disponible"));
    }
}
