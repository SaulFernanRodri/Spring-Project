package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoServicio {

    List<Medico> obtenerTodos();

    Medico crear(Medico medico);

    Medico modificar(Medico medico);

    void eliminar(Medico medico);

    Optional<Medico> buscarPorId(Long id);

    List<Medico> buscarPorNombre(String nombre);

    List<Medico> buscarPorLocalidad(String localidad);

    List<Medico> buscarPorCentroSalud(Long centroSaludId);

    //METODO PARA MEDICOS
    Medico actualizarMedico(Long medicoId, Medico datosMedico);


}