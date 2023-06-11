package med.voll.api.paciente;

public record DatosListadoPaciente (String nombre, String email, String documentoIdentidad){

    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getNombre(), paciente.getEmail(), paciente.getDocumento());
    }
}
