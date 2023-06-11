package med.voll.api.paciente;

public record DatosListadoPaciente (Long id, String nombre, String email, String documentoIdentidad){

    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
    }
}
