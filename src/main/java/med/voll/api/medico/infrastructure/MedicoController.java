package med.voll.api.medico.infrastructure;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.application.ActualizarMedicoCasoUso;
import med.voll.api.medico.application.DesactivarMedicoCasoUso;
import med.voll.api.medico.application.ObtenerListadoMedicoCasoUso;
import med.voll.api.medico.application.ObtenerMedicoCasoUso;
import med.voll.api.medico.application.RegistrarMedicoCasoUso;
import med.voll.api.medico.domain.Medico;
import med.voll.api.util.ErrorResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        Medico medico = RegistrarMedicoCasoUso.execute(datosRegistroMedico);

        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        // Retorna url de la entidad creada en la cabecera
        // Retorna la entidad creada en el cuerpo
        return ResponseEntity.created(url).body(datosRespuestaMedico); // Retorna un 201 (Created)
    }

    @GetMapping
    // @PageableDefault -> Cambia los valores por defecto de la interfaz Pageable
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 2) Pageable paginacion) {
        // Convierte toda la lista de medico a lista de DatosListadoMedico
        return ResponseEntity.ok(ObtenerListadoMedicoCasoUso.execute(paginacion).map(DatosListadoMedico::new)); // Retorna un 200 (Ok) con la pagina
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> obtenerMedico(@PathVariable Long id) {
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(ObtenerMedicoCasoUso.execute(id));
        return ResponseEntity.ok(datosRespuestaMedico); // Retorna un 200 (Ok) con la entidad
    }

    @PutMapping
    @Transactional // <- Libera la transaccion cuando se termina el método
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody DatosActualizarMedico datosActualizarMedico) {
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(ActualizarMedicoCasoUso.execute(datosActualizarMedico));
        return ResponseEntity.ok(datosRespuestaMedico); // Retorna un 200 (Ok) con el médico editado
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> eliminarMedico(@PathVariable Long id) {
        DesactivarMedicoCasoUso.execute(id);
        return ResponseEntity.noContent().build(); // Retorna un 204 (No content)
    }

    @ExceptionHandler(value = PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> propertyReferenceException(PropertyReferenceException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("La propiedad \"" + e.getPropertyName() + "\" no existe en la entidad Medico"));
    }
}
