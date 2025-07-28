package edu.unl.cc.patitas_suite.dominio.seguridad;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name="ExpedienteMedico.findByMascota", query="SELECT e FROM ExpedienteMedico e WHERE e.mascota.id = :mascotaId")
})

public class ExpedienteMedico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Información inicial al ingreso; puede ser nulo si no hay datos iniciales
    @Lob
    private String infoInicialIngreso;

    @OneToMany(mappedBy = "expedienteMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnotacionMedica> anotaciones = new ArrayList<>();

    @OneToOne(mappedBy = "expedienteMedico")
    private Mascota mascota;

    // Getters/setters
    public Long getId() { return id; }

    public String getInfoInicialIngreso() { return infoInicialIngreso; }
    public void setInfoInicialIngreso(String infoInicialIngreso) { this.infoInicialIngreso = infoInicialIngreso; }

    public List<AnotacionMedica> getAnotaciones() { return anotaciones; }
    public void setAnotaciones(List<AnotacionMedica> anotaciones) { this.anotaciones = anotaciones; }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }
}
