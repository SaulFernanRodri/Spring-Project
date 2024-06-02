package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicoDAO extends JpaRepository<Medico, Long> {

    @Query("SELECT c FROM Medico c WHERE LOWER(c.nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    List<Medico> searchByNombre(@Param("nombre") String nombre);

    @Query("SELECT m FROM Medico m JOIN m.centroSalud c WHERE LOWER(c.direccion.localidad) LIKE LOWER(concat('%', :localidad, '%'))")
    List<Medico> searchByLocalidadCentroSalud(@Param("localidad") String localidad);

    @Query("SELECT m FROM Medico m WHERE m.centroSalud.id = :centroSaludId")
    List<Medico> searchByCentroSalud(@Param("centroSaludId") Long centroSaludId);

}
