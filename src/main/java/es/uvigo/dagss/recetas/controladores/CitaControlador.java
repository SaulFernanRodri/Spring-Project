package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.servicios.CitaServicioImpl;
import es.uvigo.dagss.recetas.servicios.PrescripcionServicioImpl;
import es.uvigo.dagss.recetas.entidades.Administrador;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Paciente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/citas")
public class CitaControlador {

    private final CitaServicioImpl servicioCita;

    private final PrescripcionServicioImpl servicioPrescripcion;




    @Autowired
    public CitaControlador(CitaServicioImpl servicioCita, PrescripcionServicioImpl servicioPrescripcion) {
        this.servicioCita = servicioCita;
        this.servicioPrescripcion = servicioPrescripcion;
    }

    //METODOS PARA ADMINISTRADORES

    @GetMapping
    public List<Cita> obtenerCitas() {
        return servicioCita.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable Long id) {
        Optional<Cita> cita = servicioCita.buscarPorId(id);
        return cita.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
    }

    @GetMapping("/fecha")
    public List<Cita> obtenerCitasPorFecha(@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        return servicioCita.buscarPorFecha(fecha);
    }

    @GetMapping("/medico/{medicoId}")
    public List<Cita> obtenerCitasPorMedicoYFecha(@PathVariable Long medicoId, @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        return servicioCita.buscarPorMedicoYFecha(medicoId, fecha);
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<Cita> obtenerCitasPorPacienteYFecha(@PathVariable Long pacienteId, @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        return servicioCita.buscarPorPacienteYFecha(pacienteId, fecha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(@PathVariable Long id, @Valid @RequestBody Cita cita) {
        if (!servicioCita.buscarPorId(id).isPresent()) {
            throw new ResourceNotFoundException("Cita no encontrada con ID: " + id);
        }
        cita.setId(id);
        Cita citaActualizada = servicioCita.modificar(cita);
        return new ResponseEntity<>(citaActualizada, HttpStatus.OK);
    }

    //METODOS PARA MEDICOS
    @GetMapping("/agendaMedico/{medicoId}")
    public List<Cita> obtenerAgendaDelDiaPorMedico(@PathVariable Long medicoId) {
        return servicioCita.obtenerCitasDelDiaPorMedico(medicoId);
    }

    @PostMapping("/{citaId}/ausente")
    public ResponseEntity<Cita> marcarCitaComoAusente(@PathVariable Long citaId) {
        Cita cita = servicioCita.marcarCitaComoAusente(citaId);
        return new ResponseEntity<>(cita, HttpStatus.OK);
    }

     @GetMapping("/cita/{citaId}/informacionPaciente")
    public ResponseEntity<Paciente> obtenerInformacionPaciente(@PathVariable Long citaId) {
        Paciente paciente = servicioCita.obtenerInformacionPaciente(citaId);
        return ResponseEntity.ok(paciente);
    }

    @PostMapping("/{citaId}/completar")
    public Cita completarCita(@PathVariable Long citaId) {
        return servicioCita.completarCita(citaId);
    }

    //METODOS PARA PACIENTES

     @GetMapping("/paciente/{pacienteId}/fechas")
    public ResponseEntity<List<Cita>> obtenerCitasPlanificadasPorPaciente(@PathVariable Long pacienteId) {
        List<Cita> citasPlanificadas = servicioCita.obtenerCitasPlanificadasPorPaciente(pacienteId);
        if (citasPlanificadas != null && !citasPlanificadas.isEmpty()) {
            return ResponseEntity.ok(citasPlanificadas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/huecosDisponibles/{medicoId}")
    public ResponseEntity<List<Date>> obtenerHuecosDisponibles(@PathVariable Long medicoId, @RequestParam Date fecha) {
        List<Date> huecosDisponibles = servicioCita.buscarHuecosDisponibles(medicoId, fecha);
        return ResponseEntity.ok(huecosDisponibles);
    }

    @PostMapping("/crear")
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        Cita nuevaCita = servicioCita.crearCita(cita);
        return ResponseEntity.ok(nuevaCita);
    }
}
