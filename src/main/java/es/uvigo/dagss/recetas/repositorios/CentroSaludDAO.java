package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CentroSaludDAO extends JpaRepository<CentroSalud, Long> {


    @Query("SELECT c FROM CentroSalud c WHERE LOWER(c.nombre) LIKE LOWER(concat('%', :nombre, '%'))")
    List<CentroSalud> searchByNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM CentroSalud c WHERE LOWER(c.direccion.localidad) LIKE LOWER(concat('%', :localidad, '%'))")
    List<CentroSalud> searchByLocalidad(@Param("localidad") String localidad);
}
