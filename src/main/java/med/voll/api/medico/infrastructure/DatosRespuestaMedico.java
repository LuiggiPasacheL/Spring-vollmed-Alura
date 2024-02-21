package med.voll.api.medico.infrastructure;

import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.domain.Medico;

public record DatosRespuestaMedico (
    Long id,
    String nombre,
    String email,
    String telefono,
    String documento,
    DatosDireccion direccion
) {
    public DatosRespuestaMedico(Medico medico) {
        this(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(), new DatosDireccion(medico.getDireccion()));
    }
}
