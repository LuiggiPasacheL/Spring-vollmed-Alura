package med.voll.api.medico.application;

import med.voll.api.medico.domain.Medico;
import med.voll.api.medico.domain.MedicoRepository;
import med.voll.api.medico.infrastructure.DatosActualizarMedico;
import med.voll.api.medico.infrastructure.DatosRegistroMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public Medico actualizarMedico(DatosActualizarMedico datos) {
        Medico medico = medicoRepository.getReferenceById(datos.id());
        medico.actualizarDatos(datos);
        medicoRepository.save(medico);
        return medico;
    }

    public void desactivarMedico(Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        // medicoRepository.delete(medico); // -> Borrado fisico de la base de datos
        medico.desactivarMedico(); // -> Borrado lógico
    }

    public Page<Medico> obtenerListadoMedico(Pageable paginacion) {
        return medicoRepository.findByActivoTrue(paginacion);
    }

    public Medico obtenerMedico(Long Id) {
        return medicoRepository.getReferenceById(Id);
    }

    public Medico registrarMedico(DatosRegistroMedico datos) {
        return medicoRepository.save(new Medico(datos));
    }
}
