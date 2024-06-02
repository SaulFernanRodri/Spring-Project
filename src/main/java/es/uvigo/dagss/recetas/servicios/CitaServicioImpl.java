package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.repositorios.CitaDAO;
import es.uvigo.dagss.recetas.entidades.Cita;
import es.uvigo.dagss.recetas.entidades.EstadoCita;
import es.uvigo.dagss.recetas.entidades.Paciente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CitaServicioImpl implements CitaServicio {

    private final CitaDAO repositorioCita;

    @Autowired
    public CitaServicioImpl(CitaDAO repositorioCita) {
        this.repositorioCita = repositorioCita;
    }

    //METODOS PARA ADMINISTRADORES
    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerTodas() {
        return repositorioCita.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> buscarPorId(Long id) {
        return repositorioCita.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorFecha(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfDay = cal.getTime();

        return repositorioCita.findByFechaHoraBetween(startOfDay, endOfDay);
    }

    @Override
    @Transactional
    public Cita marcarCitaComoAusente(Long citaId) {
        Optional<Cita> optionalCita = repositorioCita.findById(citaId);
        if (optionalCita.isPresent()) {
            Cita cita = optionalCita.get();
            cita.setEstado(EstadoCita.AUSENTE);
            return repositorioCita.save(cita);
        } else {
            throw new ResourceNotFoundException("Cita no encontrada con ID: " + citaId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorPacienteYFecha(Long pacienteId, Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfDay = cal.getTime();

        return repositorioCita.findByPacienteAndFechaHoraBetween(pacienteId, startOfDay, endOfDay);
    }

    @Override
    @Transactional
    public Cita modificar(Cita cita) {
        return repositorioCita.save(cita);
    }

    //METODOS PARA MEDICOS

    @Override
    @Transactional(readOnly = true)
    public List<Cita> buscarPorMedicoYFecha(Long medicoId, Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfDay = cal.getTime();

        return repositorioCita.findByMedicoAndFechaHoraBetween(medicoId, startOfDay, endOfDay);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerCitasDelDiaPorMedico(Long medicoId) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endOfDay = calendar.getTime();

        return repositorioCita.findCitasDelDiaPorMedico(medicoId, startOfDay, endOfDay);
    }

    @Override
    @Transactional(readOnly = true)
    public Paciente obtenerInformacionPaciente(Long citaId) {
        Optional<Cita> citaOpt = repositorioCita.findById(citaId);
        if (citaOpt.isPresent()) {
            return citaOpt.get().getPacienteCitado();
        } else {
            throw new ResourceNotFoundException("Cita no encontrada con ID: " + citaId);
        }
    }

    @Override
    @Transactional
    public Cita completarCita(Long citaId) {
        Optional<Cita> citaOpt = repositorioCita.findById(citaId);
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            cita.setEstado(EstadoCita.COMPLETADA);
            return repositorioCita.save(cita);
        } else {
            throw new ResourceNotFoundException("Cita no encontrada con ID: " + citaId);
        }
    }

    //METODOS PARA PACIENTES
    @Override
    @Transactional(readOnly = true)
    public List<Cita> obtenerCitasPlanificadasPorPaciente(Long pacienteId) {
        return repositorioCita.findByPacienteCitadoIdAndEstadoOrderByFechaHoraAsc(pacienteId, EstadoCita.PLANIFICADA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Date> buscarHuecosDisponibles(Long medicoId, Date fecha) {
        // Define las horas de inicio y fin de la jornada
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);
        Date startOfDay = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);
        Date endOfDay = calendar.getTime();

        // Obtener todas las citas planificadas para ese médico en ese día
        List<Cita> citasDelDia = repositorioCita.findByMedicoAtenderaIdAndFechaHoraBetweenAndEstado(medicoId, startOfDay, endOfDay, EstadoCita.PLANIFICADA);

        // Calcular huecos disponibles
        List<Date> huecosDisponibles = new ArrayList<>();
        calendar.setTime(startOfDay);
        while (calendar.getTime().before(endOfDay)) {
            Date potentialStart = calendar.getTime();
            boolean isAvailable = true;

            // Verificar si el hueco está disponible
            for (Cita cita : citasDelDia) {
                if (cita.getFechaHora().equals(potentialStart)) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                huecosDisponibles.add(potentialStart);
            }

            // Incrementar 15 minutos para la siguiente posible cita
            calendar.add(Calendar.MINUTE, 15);
        }

        return huecosDisponibles;
    }

    @Override
    @Transactional
    public Cita crearCita(Cita cita) {
        cita.setEstado(EstadoCita.PLANIFICADA); // Estado inicial
        // La duración y otros detalles de la cita deben ser establecidos antes de llamar a este método
        return repositorioCita.save(cita);
    }


}