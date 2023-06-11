package med.voll.api.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.direccion.Direccion;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        nombre = datosRegistroPaciente.nombre();
        email = datosRegistroPaciente.email();
        documento = datosRegistroPaciente.documento();
        telefono = datosRegistroPaciente.telefono();
        direccion = new Direccion(datosRegistroPaciente.datosDireccion());
    }

    public void inhabilitar() {
        // TODO: Soft delete a paciente
    }

    public void actualizarDatos(DatosActualizarPaciente datos) {
        if (datos.nombre() != null) {
            nombre = datos.nombre();
        }
        if (datos.email() != null) {
            email = datos.email();
        }
        if (datos.documento() != null) {
            documento = datos.documento();
        }
        if (datos.telefono() != null) {
            telefono = datos.telefono();
        }
        if (datos.direccion() != null) {
            direccion = direccion.actualizarDatos(datos.direccion());
        }
    }
}