package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.servicios.MedicamentoServicioImpl;
import es.uvigo.dagss.recetas.servicios.PrescripcionServicioImpl;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescripciones")
public class PrescripcionControlador {

    @Autowired
    private MedicamentoServicioImpl medicamentoServicio;

    @Autowired
    private PrescripcionServicioImpl prescripcionServicio;

    @GetMapping("/buscarMedicamento")
    public ResponseEntity<List<Medicamento>> buscarMedicamento(@RequestParam(required = false) String nombreComercial,
                                                               @RequestParam(required = false) String principioActivo,
                                                               @RequestParam(required = false) String fabricante,
                                                               @RequestParam(required = false) String familia) {
        List<Medicamento> medicamentos = medicamentoServicio.buscarMedicamentos(nombreComercial, principioActivo, fabricante, familia);
        return ResponseEntity.ok(medicamentos);
    }

    @PostMapping("/crear")
    public ResponseEntity<Prescripcion> crearPrescripcion(@RequestBody Prescripcion prescripcion) {
        Prescripcion nuevaPrescripcion = prescripcionServicio.crearPrescripcion(prescripcion);
        return ResponseEntity.ok(nuevaPrescripcion);
    }

    @PostMapping("/{prescripcionId}/anular")
    public ResponseEntity<?> anularPrescripcion(@PathVariable Long prescripcionId) {
        try {
            Prescripcion prescripcionAnulada = prescripcionServicio.anularPrescripcion(prescripcionId);
            return ResponseEntity.ok(prescripcionAnulada);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/vigentes/paciente/{pacienteId}")
    public ResponseEntity<List<Prescripcion>> obtenerPrescripcionesVigentesPorPaciente(@PathVariable Long pacienteId) {
        List<Prescripcion> prescripcionesVigentes = prescripcionServicio.obtenerPrescripcionesVigentesPorPaciente(pacienteId);
        if (prescripcionesVigentes != null && !prescripcionesVigentes.isEmpty()) {
            return ResponseEntity.ok(prescripcionesVigentes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
