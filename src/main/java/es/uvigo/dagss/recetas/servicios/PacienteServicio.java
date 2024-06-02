package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteServicio {

    List<Paciente> obtenerTodos();

    Paciente crear(Paciente paciente);

    Paciente modificar(Paciente paciente);

    void eliminar(Paciente paciente);

    Optional<Paciente> buscarPorId(Long id);

    List<Paciente> buscarPorNombre(String nombre);

    List<Paciente> buscarPorDireccion(String direccion);

    List<Paciente> buscarPorCentroSalud(Long centroSaludId);

    List<Paciente> buscarPorMedicoAsignado(Long medicoId);

    //METODO PARA PACIENTES
    Paciente actualizarPaciente(Long pacienteId, Paciente datosPaciente);


}
