package edu.unl.cc.patitas_suite.negocios.servicios;

import edu.unl.cc.patitas_suite.dominio.seguridad.Mascota;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import edu.unl.cc.patitas_suite.dominio.seguridad.ExpedienteMedico;
import edu.unl.cc.patitas_suite.dominio.seguridad.AnotacionMedica;
import edu.unl.cc.patitas_suite.excepciones.EntityNotFoundException;

@Stateless
public class RepositorioDeExpedienteMedico {

    @Inject
    private ServicioDeCrudGenerico servicioDeCrud;

    public ExpedienteMedico save(ExpedienteMedico expediente){
        return expediente.getId() == null ? servicioDeCrud.create(expediente) : servicioDeCrud.update(expediente);
    }

    public ExpedienteMedico findById(Long id) throws EntityNotFoundException {
        ExpedienteMedico expediente = servicioDeCrud.find(ExpedienteMedico.class, id);
        if(expediente == null) throw new EntityNotFoundException("Expediente Médico no encontrado: "+id);
        return expediente;
    }

    public ExpedienteMedico findByMascotaId(Long mascotaId) throws EntityNotFoundException {
        ExpedienteMedico expedienteMedico = servicioDeCrud.find(ExpedienteMedico.class, mascotaId);
        if (expedienteMedico == null){
            throw new EntityNotFoundException("Mascota no encontrada con [" + mascotaId + "]");
        }
        return expedienteMedico;
    }

    // Agregar anotación médica ligada a expediente
    public void agregarAnotacion(ExpedienteMedico expediente, AnotacionMedica anotacion){
        expediente.getAnotaciones().add(anotacion);
        servicioDeCrud.update(expediente);
    }

    public List<AnotacionMedica> obtenerAnotaciones(Long expedienteId){
        Map<String,Object> params = Map.of("expedienteId", expedienteId);
        return servicioDeCrud.findWithNamedQuery("AnotacionMedica.findByExpediente", params);
    }
}
