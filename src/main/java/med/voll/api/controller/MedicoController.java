package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
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
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico); // Retorna un 201 (Created)
        // Retorna url de la entidad creada en la cabecera
        // Retorna la entidad creada en el cuerpo
    }

    @GetMapping
    // @PageableDefault -> Cambia los valores por defecto de la interfaz Pageable
    // public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 2) Pageable paginacion) {
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 2) Pageable paginacion) {
        // Convierte toda la lista de medico a lista de DatosListadoMedico
        // return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new)); // Retorna un 200 (Ok) con la pagina
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> obtenerMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
        return ResponseEntity.ok(datosRespuestaMedico); // Retorna un 200 (Ok) con la entidad
    }

    @PutMapping
    @Transactional // <- Libera la transaccion cuando se termina el método
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico)); // Retorna un 200 (Ok) con el médico editado
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> eliminarMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        // medicoRepository.delete(medico); // -> Borrado fisico de la base de datos
        medico.desactivarMedico(); // -> Borrado lógico
        return ResponseEntity.noContent().build(); // Retorna un 204 (No content)
    }

    @ExceptionHandler(value = PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> propertyReferenceException(PropertyReferenceException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("La propiedad \"" + e.getPropertyName() + "\" no existe en la entidad Medico"));
    }
}
