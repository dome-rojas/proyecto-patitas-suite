package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "Cliente.findLikeName",
                query = "SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE :nombre"
        )
})
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull @NotEmpty
    private String nombre;
    @NotNull @NotEmpty
    private String contacto;
    @NotNull @NotEmpty
    private String direccion;

    //private String metodoPago;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "propietario_mascota",
        joinColumns = @JoinColumn(name = "cliente_id"),
        inverseJoinColumns = @JoinColumn(name = "mascota_id"))
    private List<Mascota> mascotas;

    public Cliente(String nombre, String contacto, 
                   String direccion, String metodoPago) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.direccion = direccion;
    }

    public Cliente() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
