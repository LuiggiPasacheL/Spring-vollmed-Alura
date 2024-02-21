package med.voll.api.paciente.infrastructure;

import med.voll.api.paciente.domain.Paciente;

public record DatosRespuestaPaciente(
        Long id,
        String nombre,
        String email,
        String documentoIdentidad,
        String telefono

) {
    public DatosRespuestaPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                paciente.getDocumento(), paciente.getTelefono());
    }
}
