package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


public class ExpedienteMedico implements Serializable {

    private String infoMedica;
    private String tratamientosActivos;

    private List<RegistroSalud> registrosSalud;

    public String getTratamientosActivos() {
        return tratamientosActivos;
    }

    public void setTratamientosActivos(String tratamientosActivos) {
        this.tratamientosActivos = tratamientosActivos;
    }

    public List<RegistroSalud> getRegistrosSalud() {
        return registrosSalud;
    }

    public void setRegistrosSalud(List<RegistroSalud> registrosSalud) {
        this.registrosSalud = registrosSalud;
    }

}
