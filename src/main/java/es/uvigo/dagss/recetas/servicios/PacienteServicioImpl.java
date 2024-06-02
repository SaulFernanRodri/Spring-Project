package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.repositorios.PacienteDAO;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServicioImpl implements PacienteServicio {

    private final PacienteDAO repositorioPaciente;

    @Autowired
    public PacienteServicioImpl(PacienteDAO repositorioPaciente) {
        this.repositorioPaciente = repositorioPaciente;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> obtenerTodos() {
        return repositorioPaciente.findAll();
    }

    @Override
    @Transactional
    public Paciente crear(Paciente paciente) {
        paciente.setPassword(paciente.getDni()); // Establecer la contraseña inicial como el DNI
        return repositorioPaciente.save(paciente);
    }

    @Override
    @Transactional
    public Paciente modificar(Paciente paciente) {
        return repositorioPaciente.save(paciente);
    }

    @Override
    @Transactional
    public void eliminar(Paciente paciente) {
        repositorioPaciente.delete(paciente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorId(Long id) {
        return repositorioPaciente.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorNombre(String nombre) {
        return repositorioPaciente.searchByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorDireccion(String direccion) {
        return repositorioPaciente.searchByDireccion(direccion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorCentroSalud(Long centroSaludId) {
        return repositorioPaciente.searchByCentroSalud(centroSaludId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorMedicoAsignado(Long medicoId) {
        return repositorioPaciente.searchByMedicoAsignado(medicoId);
    }

    //METODO PARA PACIENTES
    @Override
    @Transactional
    public Paciente actualizarPaciente(Long pacienteId, Paciente datosPaciente) {
        Optional<Paciente> pacienteOpt = repositorioPaciente.findById(pacienteId);
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();

            paciente.setNombre(datosPaciente.getNombre());
            paciente.setApellidos(datosPaciente.getApellidos());
            paciente.setTelefono(datosPaciente.getTelefono());
            paciente.setEmail(datosPaciente.getEmail());
            paciente.setDireccion(datosPaciente.getDireccion());
            return repositorioPaciente.save(paciente);
        } else {
            return null;
        }
    }

}