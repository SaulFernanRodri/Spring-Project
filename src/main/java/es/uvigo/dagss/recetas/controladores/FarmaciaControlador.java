package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.servicios.FarmaciaServicioImpl;
import es.uvigo.dagss.recetas.servicios.RecetaServicioImpl;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Receta;
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
@RequestMapping("/farmacias")
public class FarmaciaControlador {

    private final FarmaciaServicioImpl servicioFarmacia;

    private final RecetaServicioImpl recetaServicio;

    @Autowired
    public FarmaciaControlador(FarmaciaServicioImpl servicioFarmacia, RecetaServicioImpl recetaServicio) {
        this.servicioFarmacia = servicioFarmacia;
        this.recetaServicio = recetaServicio;
    }

    @GetMapping
    public List<Farmacia> obtenerFarmacias() {
        return servicioFarmacia.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farmacia> obtenerFarmaciaPorId(@PathVariable Long id) {
        Optional<Farmacia> farmacia = servicioFarmacia.buscarPorId(id);
        return farmacia.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Farmacia no encontrada con ID: " + id));
    }

    @GetMapping("/buscarPorNombreEstablecimiento")
    public List<Farmacia> buscarPorNombreEstablecimiento(@RequestParam("nombreEstablecimiento") String nombreEstablecimiento) {
        return servicioFarmacia.buscarPorNombreEstablecimiento(nombreEstablecimiento);
    }

    @GetMapping("/buscarPorLocalidad")
    public List<Farmacia> buscarPorLocalidad(@RequestParam("localidad") String localidad) {
        return servicioFarmacia.buscarPorLocalidad(localidad);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Farmacia> crearFarmacia(@Valid @RequestBody Farmacia farmacia) {
        Farmacia nuevaFarmacia = servicioFarmacia.crear(farmacia);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(nuevaFarmacia.getId()).toUri();
        return ResponseEntity.created(uri).body(nuevaFarmacia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Farmacia> actualizarFarmacia(@PathVariable Long id, @Valid @RequestBody Farmacia farmacia) {
        if (!servicioFarmacia.buscarPorId(id).isPresent()) {
            throw new ResourceNotFoundException("Farmacia no encontrada con ID: " + id);
        }
        farmacia.setId(id);
        Farmacia farmaciaActualizada = servicioFarmacia.modificar(farmacia);
        return new ResponseEntity<>(farmaciaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarFarmacia(@PathVariable Long id) {
        Optional<Farmacia> farmacia = servicioFarmacia.buscarPorId(id);
        if (!farmacia.isPresent()) {
            throw new ResourceNotFoundException("Farmacia no encontrada con ID: " + id);
        }
        servicioFarmacia.eliminar(farmacia.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/pendientes/{numeroTarjetaSanitaria}")
    public ResponseEntity<List<Receta>> obtenerRecetasPendientesPorPaciente(@PathVariable String numeroTarjetaSanitaria) {
        List<Receta> recetasPendientes = recetaServicio.obtenerRecetasPendientesPorPaciente(numeroTarjetaSanitaria);
        if (recetasPendientes != null && !recetasPendientes.isEmpty()) {
            return ResponseEntity.ok(recetasPendientes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{recetaId}/servir")
    public ResponseEntity<Receta> marcarRecetaComoServida(@PathVariable Long recetaId, @RequestBody Farmacia farmaciaServidora) {
        try {
            Receta receta = recetaServicio.marcarRecetaComoServida(recetaId, farmaciaServidora);
            return ResponseEntity.ok(receta);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{farmaciaId}")
    public ResponseEntity<Farmacia> actualizarPerfilFarmacia(@PathVariable Long farmaciaId, @RequestBody Farmacia datosFarmacia) {
        Farmacia farmaciaActualizada = servicioFarmacia.actualizarPerfilFarmacia(farmaciaId, datosFarmacia);
        if (farmaciaActualizada != null) {
            return ResponseEntity.ok(farmaciaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{farmaciaId}/cambiar-contrasena")
    public ResponseEntity<?> cambiarContraseña(@PathVariable Long farmaciaId, @RequestBody String nuevaContraseña) {
        servicioFarmacia.cambiarContraseña(farmaciaId, nuevaContraseña);
        return ResponseEntity.ok().build();
    }



}
