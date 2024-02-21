package med.voll.api.paciente.infrastructure;

import med.voll.api.paciente.application.*;
import med.voll.api.paciente.domain.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datos, UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = service.registrarPaciente(datos);
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente);
        URI uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> ListadoPaciente(Pageable pageable) {
        return ResponseEntity.ok(service.obtenerListadoPaciente(pageable).map(DatosListadoPaciente::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> obtenerPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(new DatosRespuestaPaciente(service.obtenerPaciente(id)));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> editarPaciente(@RequestBody DatosActualizarPaciente datos) {
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(service.editarPaciente(datos));
        return ResponseEntity.ok(datosRespuestaPaciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) {
        service.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }

}
