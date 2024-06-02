package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Prescripcion;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PrescripcionServicio {

    List<Prescripcion> obtenerPrescripcionesVigentesPorPaciente(Long pacienteId);
    Prescripcion anularPrescripcion(Long prescripcionId);

    @Transactional
    Prescripcion crearPrescripcion(Prescripcion prescripcion);
}
