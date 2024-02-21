package med.voll.api.paciente.application;

import med.voll.api.paciente.domain.Paciente;
import med.voll.api.paciente.infrastructure.DatosActualizarPaciente;
import med.voll.api.paciente.infrastructure.DatosRegistroPaciente;
import med.voll.api.paciente.domain.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public Paciente editarPaciente(DatosActualizarPaciente datos) {
        Paciente paciente = repository.getReferenceById(datos.id());
        paciente.actualizarDatos(datos);
        return paciente;
    }

    public void eliminarPaciente(Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.inhabilitar();
    }

    public Page<Paciente> obtenerListadoPaciente(Pageable pageable) {
        return repository.findByActivoTrue(pageable);
    }

    public Paciente obtenerPaciente(Long Id) {
        return repository.getReferenceById(Id);
    }

    public Paciente registrarPaciente(DatosRegistroPaciente datos) {
        return repository.save(new Paciente(datos));
    }
}
