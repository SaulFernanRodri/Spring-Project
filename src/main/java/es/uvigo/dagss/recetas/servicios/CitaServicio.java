package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.Paciente;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CitaServicio {
    //METODOS PARA ADMINISTRADORES
    List<Cita> obtenerTodas();

    Optional<Cita> buscarPorId(Long id);

    List<Cita> buscarPorFecha(Date fecha);

    List<Cita> buscarPorMedicoYFecha(Long medicoId, Date fecha);

    List<Cita> buscarPorPacienteYFecha(Long pacienteId, Date fecha);

    Cita modificar(Cita cita);

    //METODOS PARA MEDICOS
    List<Cita> obtenerCitasDelDiaPorMedico(Long medicoId);

    Paciente obtenerInformacionPaciente(Long citaId);

    Cita marcarCitaComoAusente(Long citaId);

    Cita completarCita(Long citaId);

    //METODOS PARA USUARIOS
    List<Cita> obtenerCitasPlanificadasPorPaciente(Long pacienteId);
    List<Date> buscarHuecosDisponibles(Long medicoId, Date fecha);
    Cita crearCita(Cita cita);



}