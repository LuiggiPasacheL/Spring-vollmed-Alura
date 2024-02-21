package med.voll.api.medico.application;

import org.springframework.beans.factory.annotation.Autowired;

import med.voll.api.medico.domain.Medico;
import med.voll.api.medico.infrastructure.MedicoRepository;

/**
 * GetMedicoCasoUso
 */
public class ObtenerMedicoCasoUso {

    @Autowired
    static private MedicoRepository medicoRepository;

    public static Medico execute(Long Id) {
        return medicoRepository.getReferenceById(Id);
    }
}
