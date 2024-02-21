
package med.voll.api.medico.application;

import org.springframework.beans.factory.annotation.Autowired;

import med.voll.api.medico.domain.Medico;
import med.voll.api.medico.infrastructure.DatosActualizarMedico;
import med.voll.api.medico.infrastructure.MedicoRepository;

/**
 * ActualizarMedicoCasoUso
 */
public class ActualizarMedicoCasoUso {

    @Autowired
    static private MedicoRepository medicoRepository;

    public static Medico execute(DatosActualizarMedico datos) {
        Medico medico = medicoRepository.getReferenceById(datos.id());
        medico.actualizarDatos(datos);
        medicoRepository.save(medico);
        return medico;
    }
}
