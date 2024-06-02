package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicamentoDAO extends JpaRepository<Medicamento, Long> {

    @Query("SELECT m FROM Medicamento m WHERE LOWER(m.nombreComercial) LIKE LOWER(concat('%', :nombreComercial, '%'))")
    List<Medicamento> searchByNombreComercial(@Param("nombreComercial") String nombreComercial);

    @Query("SELECT m FROM Medicamento m WHERE LOWER(m.principioActivo) LIKE LOWER(concat('%', :principioActivo, '%'))")
    List<Medicamento> searchByPrincipioActivo(@Param("principioActivo") String principioActivo);

    @Query("SELECT m FROM Medicamento m WHERE LOWER(m.fabricante) LIKE LOWER(concat('%', :fabricante, '%'))")
    List<Medicamento> searchByFabricante(@Param("fabricante") String fabricante);

    @Query("SELECT m FROM Medicamento m WHERE LOWER(m.familia) LIKE LOWER(concat('%', :familia, '%'))")
    List<Medicamento> searchByFamilia(@Param("familia") String familia);

    @Query("SELECT m FROM Medicamento m WHERE " +
            "(LOWER(m.nombreComercial) LIKE LOWER(CONCAT('%', :nombreComercial, '%')) OR :nombreComercial IS NULL) AND " +
            "(LOWER(m.principioActivo) LIKE LOWER(CONCAT('%', :principioActivo, '%')) OR :principioActivo IS NULL) AND " +
            "(LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :fabricante, '%')) OR :fabricante IS NULL) AND " +
            "(LOWER(m.familia) LIKE LOWER(CONCAT('%', :familia, '%')) OR :familia IS NULL)")
    List<Medicamento> buscarMedicamentos(
            @Param("nombreComercial") String nombreComercial,
            @Param("principioActivo") String principioActivo,
            @Param("fabricante") String fabricante,
            @Param("familia") String familia);
}
