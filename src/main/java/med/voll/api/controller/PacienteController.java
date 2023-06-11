package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public void registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datos) {
        repository.save(new Paciente(datos));
    }

    @GetMapping
    public Page<DatosListadoPaciente> ListadoPaciente(Pageable pageable){
        return repository.findAll(pageable).map(DatosListadoPaciente::new);
    }

    @PutMapping
    @Transactional
    public void editarPaciente(@RequestBody DatosActualizarPaciente datos) {
        Paciente paciente = repository.getReferenceById(datos.id());
        paciente.actualizarDatos(datos);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarPaciente(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.inhabilitar();
    }
}
