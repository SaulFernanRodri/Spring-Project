package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Farmacia;

import java.util.List;
import java.util.Optional;

public interface FarmaciaServicio {

    List<Farmacia> obtenerTodas();

    Farmacia crear(Farmacia farmacia);

    Farmacia modificar(Farmacia farmacia);

    void eliminar(Farmacia farmacia);

    Optional<Farmacia> buscarPorId(Long id);

    List<Farmacia> buscarPorNombreEstablecimiento(String nombreEstablecimiento);

    List<Farmacia> buscarPorLocalidad(String localidad);

    Farmacia actualizarPerfilFarmacia(Long farmaciaId, Farmacia datosFarmacia);

    void cambiarContraseña(Long farmaciaId, String nuevaContraseña);
}
