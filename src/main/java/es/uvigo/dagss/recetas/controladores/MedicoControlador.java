package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.servicios.MedicoServicioImpl;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
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
@RequestMapping("/medicos")
public class MedicoControlador {

    private final MedicoServicioImpl servicioMedico;

    @Autowired
    public MedicoControlador(MedicoServicioImpl servicioMedico) {
        this.servicioMedico = servicioMedico;
    }

    @GetMapping
    public List<Medico> obtenerMedicos() {
        return servicioMedico.obtenerTodos();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable("id") Long id) {
        Optional<Medico> medico = servicioMedico.buscarPorId(id);
        if (medico.isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado");
        } else {
            return new ResponseEntity<>(medico.get(), HttpStatus.OK);
        }
    }

    @GetMapping("/buscarPorNombre")
    public List<Medico> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return servicioMedico.buscarPorNombre(nombre);
    }

    @GetMapping("/buscarPorLocalidad")
    public List<Medico> buscarPorLocalidad(@RequestParam("localidad") String localidad) {
        return servicioMedico.buscarPorLocalidad(localidad);
    }

    @GetMapping("/buscarPorCentroSalud")
    public List<Medico> buscarPorCentroSalud(@RequestParam("centroSaludId") Long centroSaludId) {
        return servicioMedico.buscarPorCentroSalud(centroSaludId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medico> crear(@Valid @RequestBody Medico medico) {
        Medico nuevoMedico = servicioMedico.crear(medico);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(nuevoMedico.getId()).toUri();
        return ResponseEntity.created(uri).body(nuevoMedico);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Medico> medico = servicioMedico.buscarPorId(id);
        if (medico.isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado");
        } else {
            servicioMedico.eliminar(medico.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medico> modificar(@PathVariable("id") Long id, @Valid @RequestBody Medico medico) {
        Optional<Medico> medicoExistente = servicioMedico.buscarPorId(id);
        if (medicoExistente.isEmpty()) {
            throw new ResourceNotFoundException("Médico no encontrado");
        } else {
            medico.setId(id);
            Medico medicoActualizado = servicioMedico.modificar(medico);
            return new ResponseEntity<>(medicoActualizado, HttpStatus.OK);
        }
    }

    //METODO PARA MEDICOS
    @PutMapping("/{medicoId}")
    public ResponseEntity<Medico> actualizarMedico(@PathVariable Long medicoId, @RequestBody Medico datosMedico) {
        Medico medicoActualizado = servicioMedico.actualizarMedico(medicoId, datosMedico);
        if (medicoActualizado != null) {
            return ResponseEntity.ok(medicoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}