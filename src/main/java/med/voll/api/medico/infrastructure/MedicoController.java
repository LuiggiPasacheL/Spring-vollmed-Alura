package med.voll.api.medico.infrastructure;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.application.MedicoService;
import med.voll.api.medico.domain.Medico;
import med.voll.api.util.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MedicoService service;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        Medico medico = service.registrarMedico(datosRegistroMedico);

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
        return ResponseEntity.ok(service.obtenerListadoMedico(paginacion).map(DatosListadoMedico::new)); // Retorna un 200 (Ok) con la pagina
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> obtenerMedico(@PathVariable Long id) {
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(service.obtenerMedico(id));
        return ResponseEntity.ok(datosRespuestaMedico); // Retorna un 200 (Ok) con la entidad
    }

    @PutMapping
    @Transactional // <- Libera la transaccion cuando se termina el método
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody DatosActualizarMedico datosActualizarMedico) {
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(service.actualizarMedico(datosActualizarMedico));
        return ResponseEntity.ok(datosRespuestaMedico); // Retorna un 200 (Ok) con el médico editado
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> eliminarMedico(@PathVariable Long id) {
        service.desactivarMedico(id);
        return ResponseEntity.noContent().build(); // Retorna un 204 (No content)
    }

    @ExceptionHandler(value = PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> handlePropertyReferenceException(PropertyReferenceException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("La propiedad \"" + e.getPropertyName() + "\" no existe en la entidad Medico"));
    }
}
