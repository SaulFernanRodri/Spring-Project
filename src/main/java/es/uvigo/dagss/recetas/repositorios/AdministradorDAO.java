package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

                                        //<clase del DAO, Tipo de clave (Int, String, Double...)
public interface AdministradorDAO extends JpaRepository<Administrador,Long> {


}
