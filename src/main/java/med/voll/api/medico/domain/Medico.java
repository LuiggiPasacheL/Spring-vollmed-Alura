package med.voll.api.medico.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.direccion.Direccion;
import med.voll.api.medico.infrastructure.DatosActualizarMedico;
import med.voll.api.medico.infrastructure.DatosRegistroMedico;

@Entity
@Table(name = "medicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        activo = true;
        nombre = datosRegistroMedico.nombre();
        email = datosRegistroMedico.email();
        telefono = datosRegistroMedico.telefono();
        documento = datosRegistroMedico.documento();
        especialidad = datosRegistroMedico.especialidad();
        direccion = new Direccion(datosRegistroMedico.direccion());
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if (datosActualizarMedico.nombre() != null) {
            nombre = datosActualizarMedico.nombre();
        }
        if (datosActualizarMedico.documento() != null) {
            documento = datosActualizarMedico.documento();
        }
        if (datosActualizarMedico.direccion() != null) {
            direccion = direccion.actualizarDatos(datosActualizarMedico.direccion());
        }
    }

    public void desactivarMedico() {
        activo = false;
    }
}
