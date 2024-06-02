package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.EstadoReceta;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaDAO extends JpaRepository<Receta, Long> {

    List<Receta> findByPrescripcionId(Long prescripcionId);

    List<Receta> findByPrescripcionPacienteIdAndEstadoOrderByFechaValidezInicialAsc(Long pacienteId, EstadoReceta estado);

    List<Receta> findByPrescripcionPacienteNumeroTarjetaSanitariaAndEstadoOrderByFechaValidezInicialAsc(String numeroTarjetaSanitaria, EstadoReceta estado);


}
