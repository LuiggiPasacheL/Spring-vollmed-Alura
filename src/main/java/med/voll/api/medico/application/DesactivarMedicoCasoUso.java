
package med.voll.api.medico.application;

import org.springframework.beans.factory.annotation.Autowired;

import med.voll.api.medico.domain.Medico;
import med.voll.api.medico.infrastructure.MedicoRepository;

/**
 * DesactivarMedicoCasoUso
 */
public class DesactivarMedicoCasoUso {
    
    @Autowired
    static private MedicoRepository medicoRepository;

    public static void execute(Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        // medicoRepository.delete(medico); // -> Borrado fisico de la base de datos
        medico.desactivarMedico(); // -> Borrado lógico
    }
}
