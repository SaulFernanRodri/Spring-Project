package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.servicios.PacienteServicioImpl;
import es.uvigo.dagss.recetas.entidades.Paciente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteControlador {

    private final PacienteServicioImpl servicioPaciente;

    @Autowired
    public PacienteControlador(PacienteServicioImpl servicioPaciente) {
        this.servicioPaciente = servicioPaciente;
    }

    @GetMapping
    public List<Paciente> obtenerPacientes() {
        return servicioPaciente.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPacientePorId(@PathVariable Long id) {
        Optional<Paciente> paciente = servicioPaciente.buscarPorId(id);
        return paciente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
    }

    @GetMapping("/buscarPorNombre")
    public List<Paciente> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return servicioPaciente.buscarPorNombre(nombre);
    }

    @GetMapping("/buscarPorDireccion")
    public List<Paciente> buscarPorDireccion(@RequestParam("direccion") String direccion) {
        return servicioPaciente.buscarPorDireccion(direccion);
    }

    @GetMapping("/buscarPorCentroSalud")
    public List<Paciente> buscarPorCentroSalud(@RequestParam("centroSaludId") Long centroSaludId) {
        return servicioPaciente.buscarPorCentroSalud(centroSaludId);
    }

    @GetMapping("/buscarPorMedicoAsignado")
    public List<Paciente> buscarPorMedicoAsignado(@RequestParam("medicoId") Long medicoId) {
        return servicioPaciente.buscarPorMedicoAsignado(medicoId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paciente> crearPaciente(@Valid @RequestBody Paciente paciente) {
        Paciente nuevoPaciente = servicioPaciente.crear(paciente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(nuevoPaciente.getId()).toUri();
        return ResponseEntity.created(uri).body(nuevoPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> modificar(@PathVariable Long id, @Valid @RequestBody Paciente paciente) {
        if (!servicioPaciente.buscarPorId(id).isPresent()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        paciente.setId(id);
        Paciente pacienteActualizado = servicioPaciente.modificar(paciente);
        return new ResponseEntity<>(pacienteActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarPaciente(@PathVariable Long id) {
        Optional<Paciente> paciente = servicioPaciente.buscarPorId(id);
        if (!paciente.isPresent()) {
            throw new ResourceNotFoundException("Paciente no encontrado con ID: " + id);
        }
        servicioPaciente.eliminar(paciente.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //METODO PARA PACIENTES
    @PutMapping("/{pacienteId}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long pacienteId, @RequestBody Paciente datosPaciente) {
        Paciente pacienteActualizado = servicioPaciente.actualizarPaciente(pacienteId, datosPaciente);
        if (pacienteActualizado != null) {
            return ResponseEntity.ok(pacienteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}