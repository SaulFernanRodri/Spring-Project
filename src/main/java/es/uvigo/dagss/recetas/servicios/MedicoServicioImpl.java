package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.repositorios.MedicoDAO;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServicioImpl implements MedicoServicio {

    private final MedicoDAO repositorioMedico;

    @Autowired
    public MedicoServicioImpl(MedicoDAO repositorioMedico) {
        this.repositorioMedico = repositorioMedico;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> obtenerTodos() {
        return repositorioMedico.findAll();
    }

    @Override
    @Transactional
    public Medico crear(Medico medico) {
        medico.setPassword(medico.getNumeroColegiado());
        return repositorioMedico.save(medico);
    }

    @Override
    @Transactional
    public Medico modificar(Medico medico) {
        return repositorioMedico.save(medico);
    }

    @Override
    @Transactional
    public void eliminar(Medico medico) {
        repositorioMedico.delete(medico);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medico> buscarPorId(Long id) {
        return repositorioMedico.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarPorNombre(String nombre) {
        return repositorioMedico.searchByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarPorLocalidad(String localidad) {
        return repositorioMedico.searchByLocalidadCentroSalud(localidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medico> buscarPorCentroSalud(Long centroSaludId) {
        return repositorioMedico.searchByCentroSalud(centroSaludId);
    }

    @Override
    @Transactional
    public Medico actualizarMedico(Long medicoId, Medico datosMedico) {
        Optional<Medico> medicoOpt = repositorioMedico.findById(medicoId);
        if (medicoOpt.isPresent()) {
            Medico medico = medicoOpt.get();

            medico.setNombre(datosMedico.getNombre());
            medico.setApellidos(datosMedico.getApellidos());
            medico.setTelefono(datosMedico.getTelefono());
            medico.setEmail(datosMedico.getEmail());

            return repositorioMedico.save(medico);
        } else {
            return null;
        }
    }

}