package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

public class Mascota {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate edad;
    private String estado;

    private Cliente propietario;

    private Habitacion habitacion;

    private ExpedienteMedico expedienteMedico;

    private void validarRestriccionDeNombre(String text) throws IllegalArgumentException{
        if (text == null || text.isEmpty()){
            throw new IllegalArgumentException("Valor reequerido para nombre");
        }
    }
    public Mascota(String nombre, String especie,
                   String raza, LocalDate edad, String necesidadesEspeciales,
                   String historialVacunas, String estado)
            throws IllegalArgumentException{
        validarRestriccionDeNombre(nombre);
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.estado = estado;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExpedienteMedico getExpedienteMedico() {
        return expedienteMedico;
    }

    public void setExpedienteMedico(ExpedienteMedico expedienteMedico) {
        this.expedienteMedico = expedienteMedico;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getEdad() {
        return edad;
    }

    public void setEdad(LocalDate edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mascota mascota = (Mascota) o;
        return Objects.equals(id, mascota.id) && Objects.equals(nombre, mascota.nombre) && Objects.equals(especie, mascota.especie) && Objects.equals(raza, mascota.raza) && Objects.equals(edad, mascota.edad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, especie, raza, edad);
    }
}
