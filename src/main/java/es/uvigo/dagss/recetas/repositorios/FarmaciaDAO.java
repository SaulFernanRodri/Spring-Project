package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FarmaciaDAO extends JpaRepository<Farmacia, Long> {

    @Query("SELECT f FROM Farmacia f WHERE LOWER(f.nombreEstablecimiento) LIKE LOWER(concat('%', :nombreEstablecimiento, '%'))")
    List<Farmacia> searchByNombreEstablecimiento(@Param("nombreEstablecimiento") String nombreEstablecimiento);

    @Query("SELECT f FROM Farmacia f WHERE LOWER(f.direccion.localidad) LIKE LOWER(concat('%', :localidad, '%'))")
    List<Farmacia> searchByLocalidad(@Param("localidad") String localidad);
}
