package edu.unl.cc.patitas_suite.dominio.seguridad;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = "Mascota.findByNombreAndPropietario",
                query = "SELECT m FROM Mascota m WHERE LOWER(m.nombre) = :nombre AND m.propietario.id = :propietarioId"),
    @NamedQuery(name = "Mascota.findByCuidador",
                query = "SELECT m FROM Mascota m WHERE m.cuidador.id = :cuidadorId"),
        @NamedQuery(
                name = "Mascota.findAll",
                query = "SELECT m FROM Mascota m"
        )
})
public class Mascota implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotEmpty
    private String nombre;

    @NotNull @NotEmpty
    private String especie;

    @NotNull @NotEmpty
    private String raza;

    @NotNull
    private Float peso;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Complexion complexion;


    private LocalDate fechaNacimiento;

    @ManyToOne(optional = true)
    @JoinColumn(name = "propietario_id", nullable = true)
    private Cliente propietario;

    @ManyToOne
    @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;

    private ExpedienteMedico expedienteMedico;

    private EstadoMascota estado=EstadoMascota.SANO;

    @ManyToOne
    @JoinColumn(name = "cuidador_id")
    private Usuario cuidador;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    public Mascota() {

    }

    private void validarRestriccionDeNombre(String text) throws IllegalArgumentException{
        if (text == null || text.isEmpty()){
            throw new IllegalArgumentException("Valor requerido para nombre");
        }
    }

    public Mascota(String nombre, String especie,
                   String raza, LocalDate fechaNacimiento, String necesidadesEspeciales,
                   String historialVacunas, String estado, float peso)
            throws IllegalArgumentException{
        validarRestriccionDeNombre(nombre);
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Complexion getComplexion() {
        return complexion;
    }

    public void setComplexion(Complexion complexion) {
        this.complexion = complexion;
    }

    public EstadoMascota getEstado() {
        return estado;
    }

    public void setEstado(EstadoMascota estado) {
        this.estado = estado;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Usuario getCuidador() {
        return cuidador;
    }

    public void setCuidador(Usuario cuidador) {
        this.cuidador = cuidador;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate edad) {
        this.fechaNacimiento = edad;
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
        return Objects.equals(nombre, mascota.nombre) && Objects.equals(especie, mascota.especie) && Objects.equals(raza, mascota.raza) && Objects.equals(fechaNacimiento, mascota.fechaNacimiento) && Objects.equals(propietario, mascota.propietario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, especie, raza, fechaNacimiento, propietario);
    }
}
