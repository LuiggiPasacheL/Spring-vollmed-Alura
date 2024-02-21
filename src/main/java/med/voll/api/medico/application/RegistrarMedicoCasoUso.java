
package med.voll.api.medico.application;

import org.springframework.beans.factory.annotation.Autowired;

import med.voll.api.medico.domain.Medico;
import med.voll.api.medico.infrastructure.DatosRegistroMedico;
import med.voll.api.medico.infrastructure.MedicoRepository;

/**
 * RegistrarMedicoCasoUso
 */
public class RegistrarMedicoCasoUso {

    @Autowired
    static private MedicoRepository medicoRepository;

    public static Medico execute(DatosRegistroMedico datos) {
        return medicoRepository.save(new Medico(datos));
    }
}
