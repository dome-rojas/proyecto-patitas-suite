package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.List;

public class ExpedienteMedico {
    @Id
    @GeneratedValue
    private Long id;
    private String alergias;
    private String tratamientosActivos;
    private LocalDate ultimaRevision;
    private String dietaEspecial;

    private Mascota mascota;

    private List<RegistroSalud> registrosSalud;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getTratamientosActivos() {
        return tratamientosActivos;
    }

    public void setTratamientosActivos(String tratamientosActivos) {
        this.tratamientosActivos = tratamientosActivos;
    }

    public LocalDate getUltimaRevision() {
        return ultimaRevision;
    }

    public void setUltimaRevision(LocalDate ultimaRevision) {
        this.ultimaRevision = ultimaRevision;
    }

    public String getDietaEspecial() {
        return dietaEspecial;
    }

    public void setDietaEspecial(String dietaEspecial) {
        this.dietaEspecial = dietaEspecial;
    }

    public List<RegistroSalud> getRegistrosSalud() {
        return registrosSalud;
    }

    public void setRegistrosSalud(List<RegistroSalud> registrosSalud) {
        this.registrosSalud = registrosSalud;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
