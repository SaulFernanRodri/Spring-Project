package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.servicios.CentroSaludServicioImpl;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
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
@RequestMapping("/centrosSalud")
public class CentroSaludControlador {

    private final CentroSaludServicioImpl servicioCentroSalud;

    @Autowired
    public CentroSaludControlador(CentroSaludServicioImpl servicioCentroSalud) {
        this.servicioCentroSalud = servicioCentroSalud;
    }

    @GetMapping
    public List<CentroSalud> obtenerCentrosSalud() {
        return servicioCentroSalud.obtenerTodos();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<CentroSalud> buscarPorId(@PathVariable("id") Long id) {
        Optional<CentroSalud> centroSalud = servicioCentroSalud.buscarPorId(id);
        if (centroSalud.isEmpty()) {
            throw new ResourceNotFoundException("Centro de salud no encontrado");
        } else {
            return new ResponseEntity<>(centroSalud.get(), HttpStatus.OK);
        }
    }

    @GetMapping("/buscarPorNombre")
    public List<CentroSalud> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return servicioCentroSalud.buscarPorNombre(nombre);
    }

    @GetMapping("/buscarPorLocalidad")
    public List<CentroSalud> buscarPorLocalidad(@RequestParam("localidad") String localidad) {
        return servicioCentroSalud.buscarPorLocalidad(localidad);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CentroSalud> crear(@Valid @RequestBody CentroSalud centroSalud) {
        CentroSalud nuevoCentroSalud = servicioCentroSalud.crear(centroSalud);
        URI uri = crearURICentroSalud(nuevoCentroSalud);

        return ResponseEntity.created(uri).body(nuevoCentroSalud);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<CentroSalud> centroSalud = servicioCentroSalud.buscarPorId(id);

        if (centroSalud.isEmpty()) {
            throw new ResourceNotFoundException("Centro de salud no encontrado");
        } else {
            servicioCentroSalud.eliminar(centroSalud.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CentroSalud> modificar(@PathVariable("id") Long id, @Valid @RequestBody CentroSalud centroSalud) {
        Optional<CentroSalud> centroSaludOptional = servicioCentroSalud.buscarPorId(id);

        if (centroSaludOptional.isEmpty()) {
            throw new ResourceNotFoundException("Centro de salud no encontrado");
        } else {
            CentroSalud nuevoCentroSalud = servicioCentroSalud.modificar(centroSalud);
            return new ResponseEntity<>(nuevoCentroSalud, HttpStatus.OK);
        }
    }

    // Construye la URI del nuevo recurso creado con POST
    private URI crearURICentroSalud(CentroSalud centroSalud) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(centroSalud.getId())
                .toUri();
    }
}