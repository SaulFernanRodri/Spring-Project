package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CitaDAO extends JpaRepository<Cita, Long> {
    //METODOS PARA ADMINISTRADORES

    List<Cita> findByFechaHoraBetween(Date start, Date end);

    @Query("SELECT c FROM Cita c WHERE c.medicoAtendera.id = :medicoId AND c.fechaHora BETWEEN :start AND :end")
    List<Cita> findByMedicoAndFechaHoraBetween(@Param("medicoId") Long medicoId, @Param("start") Date start, @Param("end") Date end);

    @Query("SELECT c FROM Cita c WHERE c.pacienteCitado.id = :pacienteId AND c.fechaHora BETWEEN :start AND :end")
    List<Cita> findByPacienteAndFechaHoraBetween(@Param("pacienteId") Long pacienteId, @Param("start") Date start, @Param("end") Date end);

    //METODO PARA MEDICOS
    @Query("SELECT c FROM Cita c WHERE c.medicoAtendera.id = :medicoId AND c.fechaHora BETWEEN :startOfDay AND :endOfDay")
    List<Cita> findCitasDelDiaPorMedico(@Param("medicoId") Long medicoId, @Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);

    List<Cita> findByPacienteCitadoIdAndEstadoOrderByFechaHoraAsc(Long pacienteId, EstadoCita estado);
    //METODO PARA PACIENTES
    List<Cita> findByMedicoAtenderaIdAndFechaHoraBetweenAndEstado(Long medicoId, Date startOfDay, Date endOfDay, EstadoCita estado);

}
