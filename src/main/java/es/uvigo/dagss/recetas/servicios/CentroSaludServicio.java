package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.CentroSalud;

import java.util.List;
import java.util.Optional;

public interface CentroSaludServicio {

    List<CentroSalud> obtenerTodos();

    CentroSalud crear(CentroSalud centroSalud);

    CentroSalud modificar(CentroSalud centroSalud);

    void eliminar(CentroSalud centroSalud);

    Optional<CentroSalud> buscarPorId(Long id);

    List<CentroSalud> buscarPorNombre(String nombre);

    List<CentroSalud> buscarPorLocalidad(String localidad);

}
