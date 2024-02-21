package med.voll.api.paciente.infrastructure;

import med.voll.api.paciente.domain.Paciente;

public record DatosListadoPaciente (Long id, String nombre, String email, String documentoIdentidad){

    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
    }
}
