
package med.voll.api.medico.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import med.voll.api.medico.domain.Medico;
import med.voll.api.medico.infrastructure.MedicoRepository;

/**
 * GetListadoMedicoCasoUso
 */
public class ObtenerListadoMedicoCasoUso {

    @Autowired
    static private MedicoRepository medicoRepository;

    public static Page<Medico> execute(Pageable paginacion) {
        // Convierte toda la lista de medico a lista de DatosListadoMedico
        // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return medicoRepository.findByActivoTrue(paginacion);
    }
}
