package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PacienteDAO extends JpaRepository<Paciente, Long> {

    @Query("SELECT p FROM Paciente p WHERE LOWER(p.nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    List<Paciente> searchByNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Paciente p WHERE LOWER(p.direccion) LIKE LOWER(concat('%', :direccion, '%'))")
    List<Paciente> searchByDireccion(@Param("direccion") String direccion);

    @Query("SELECT p FROM Paciente p WHERE p.centroSalud.id = :centroSaludId")
    List<Paciente> searchByCentroSalud(@Param("centroSaludId") Long centroSaludId);

    @Query("SELECT p FROM Paciente p WHERE p.medicoAsignado.id = :medicoId")
    List<Paciente> searchByMedicoAsignado(@Param("medicoId") Long medicoId);
}