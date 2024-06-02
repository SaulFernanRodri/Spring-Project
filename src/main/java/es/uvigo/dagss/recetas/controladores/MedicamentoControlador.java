package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.servicios.MedicamentoServicioImpl;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoControlador {

    private final MedicamentoServicioImpl servicioMedicamento;

    @Autowired
    public MedicamentoControlador(MedicamentoServicioImpl servicioMedicamento) {
        this.servicioMedicamento = servicioMedicamento;
    }

    @GetMapping
    public List<Medicamento> obtenerMedicamentos() {
        return servicioMedicamento.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> obtenerMedicamentoPorId(@PathVariable Long id) {
        Optional<Medicamento> medicamento = servicioMedicamento.buscarPorId(id);
        return medicamento.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con ID: " + id));
    }

    @GetMapping("/buscarPorNombreComercial")
    public List<Medicamento> buscarPorNombreComercial(@RequestParam("nombreComercial") String nombreComercial) {
        return servicioMedicamento.buscarPorNombreComercial(nombreComercial);
    }

    @GetMapping("/buscarPorPrincipioActivo")
    public List<Medicamento> buscarPorPrincipioActivo(@RequestParam("principioActivo") String principioActivo) {
        return servicioMedicamento.buscarPorPrincipioActivo(principioActivo);
    }

    @GetMapping("/buscarPorFabricante")
    public List<Medicamento> buscarPorFabricante(@RequestParam("fabricante") String fabricante) {
        return servicioMedicamento.buscarPorFabricante(fabricante);
    }

    @GetMapping("/buscarPorFamilia")
    public List<Medicamento> buscarPorFamilia(@RequestParam("familia") String familia) {
        return servicioMedicamento.buscarPorFamilia(familia);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medicamento> crearMedicamento(@Valid @RequestBody Medicamento medicamento) {
        Medicamento nuevoMedicamento = servicioMedicamento.crear(medicamento);
        return new ResponseEntity<>(nuevoMedicamento, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> actualizarMedicamento(@PathVariable Long id, @Valid @RequestBody Medicamento medicamento) {
        if (!servicioMedicamento.buscarPorId(id).isPresent()) {
            throw new ResourceNotFoundException("Medicamento no encontrado con ID: " + id);
        }
        medicamento.setId(id);
        Medicamento medicamentoActualizado = servicioMedicamento.modificar(medicamento);
        return new ResponseEntity<>(medicamentoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarMedicamento(@PathVariable Long id) {
        Optional<Medicamento> medicamento = servicioMedicamento.buscarPorId(id);
        if (!medicamento.isPresent()) {
            throw new ResourceNotFoundException("Medicamento no encontrado con ID: " + id);
        }
        servicioMedicamento.eliminar(medicamento.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
