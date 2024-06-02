package es.uvigo.dagss.recetas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import es.uvigo.dagss.recetas.entidades.Prescripcion;


import java.util.Date;
import java.util.List;

public interface PrescripcionDAO extends JpaRepository<Prescripcion, Long> {

    @Query("SELECT p FROM Prescripcion p WHERE p.paciente.id = :pacienteId AND p.fechaFin >= :fechaActual ORDER BY p.fechaInicio")
    List<Prescripcion> findPrescripcionesVigentesPorPaciente(@Param("pacienteId") Long pacienteId, @Param("fechaActual") Date fechaActual);
}
