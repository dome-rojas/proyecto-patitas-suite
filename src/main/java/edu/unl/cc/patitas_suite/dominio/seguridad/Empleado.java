package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Objects;

public class Empleado {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private String rol;
    private String turno;

    private List<Mascota> mascotasAsignadas;

    private List<Habitacion> habitacionesACargo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public List<Habitacion> getHabitacionesACargo() {
        return habitacionesACargo;
    }

    public void setHabitacionesACargo(List<Habitacion> habitacionesACargo) {
        this.habitacionesACargo = habitacionesACargo;
    }

    public List<Mascota> getMascotasAsignadas() {
        return mascotasAsignadas;
    }

    public void setMascotasAsignadas(List<Mascota> mascotasAsignadas) {
        this.mascotasAsignadas = mascotasAsignadas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(id, empleado.id) && Objects.equals(nombre, empleado.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
