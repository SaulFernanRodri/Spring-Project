package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.servicios.RecetaServicioImpl;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recetas")
public class RecetaControlador {

    @Autowired
    private RecetaServicioImpl recetaServicio;

    @GetMapping("/pendientes/paciente/{pacienteId}")
    public ResponseEntity<List<Receta>> obtenerRecetasPendientesPorPaciente(@PathVariable Long pacienteId) {
        List<Receta> recetasPendientes = recetaServicio.obtenerRecetasPendientesPorPaciente(pacienteId);
        if (recetasPendientes != null && !recetasPendientes.isEmpty()) {
            return ResponseEntity.ok(recetasPendientes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pendientes/pacienteTarjeta/{numeroTarjetaSanitaria}")
    public ResponseEntity<List<Receta>> obtenerRecetasPendientesPorPaciente(@PathVariable String numeroTarjetaSanitaria) {
        List<Receta> recetasPendientes = recetaServicio.obtenerRecetasPendientesPorPaciente(numeroTarjetaSanitaria);
        if (recetasPendientes != null && !recetasPendientes.isEmpty()) {
            return ResponseEntity.ok(recetasPendientes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/marcarServida/{recetaId}/farmacia/{farmaciaId}")
    public ResponseEntity<Receta> marcarRecetaComoServida(@PathVariable Long recetaId, @PathVariable Long farmaciaId) {
        Farmacia farmacia = new Farmacia(); // Aquí deberías obtener la farmacia real de tu base de datos o contexto
        farmacia.setId(farmaciaId); // Asumiendo que tienes un setId en tu entidad Farmacia
        Receta receta = recetaServicio.marcarRecetaComoServida(recetaId, farmacia);
        return ResponseEntity.ok(receta);
    }

    @PostMapping("/generarPlanDeRecetas")
    public ResponseEntity<List<Receta>> generarPlanDeRecetas(@RequestBody Prescripcion prescripcion) {
        List<Receta> planDeRecetas = recetaServicio.generarPlanDeRecetas(prescripcion);
        if (planDeRecetas != null && !planDeRecetas.isEmpty()) {
            return ResponseEntity.ok(planDeRecetas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
