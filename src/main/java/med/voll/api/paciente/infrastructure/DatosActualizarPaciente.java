package med.voll.api.paciente.infrastructure;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import med.voll.api.direccion.DatosDireccion;

public record DatosActualizarPaciente(
        @NotNull
        Long id,
        String nombre,
        @Email
        String email,
        String documento,
        String telefono,
        DatosDireccion direccion) {

}
