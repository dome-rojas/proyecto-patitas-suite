package edu.unl.cc.patitas_suite.dominio.seguridad;

import edu.unl.cc.patitas_suite.dominio.comun.Usuario;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@NamedQueries({
        @NamedQuery(name="AnotacionMedica.findByExpediente", query="SELECT a FROM AnotacionMedica a WHERE a.expedienteMedico.id = :expedienteId ORDER BY a.fechaHora DESC")
})

public class AnotacionMedica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String texto;

    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ficha_medica_id")
    private ExpedienteMedico expedienteMedico;

    public AnotacionMedica(String texto, Usuario usuario, ExpedienteMedico expedienteMedico) {
        this.texto = texto;
        this.fechaHora = LocalDateTime.now();
        this.usuario = usuario;
        this.expedienteMedico = expedienteMedico;
    }

    public AnotacionMedica() {}

    public AnotacionMedica(String texto, LocalDateTime fechaHora, Usuario usuario, ExpedienteMedico expedienteMedico) {
        this.texto = texto;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.expedienteMedico = expedienteMedico;
    }

    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public ExpedienteMedico getExpedienteMedico() { return expedienteMedico; }
    public void setExpedienteMedico(ExpedienteMedico expedienteMedico) { this.expedienteMedico = expedienteMedico; }
}
