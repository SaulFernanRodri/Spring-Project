package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.repositorios.CentroSaludDAO;
import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CentroSaludServicioImpl implements CentroSaludServicio {

    private final CentroSaludDAO repositorioCentroSalud;

    @Autowired
    public CentroSaludServicioImpl(CentroSaludDAO repositorioCentroSalud) {
        this.repositorioCentroSalud = repositorioCentroSalud;
    }

    @Override
    public List<CentroSalud> obtenerTodos() {
        return repositorioCentroSalud.findAll();
    }

    @Override
    @Transactional
    public CentroSalud crear(CentroSalud centroSalud) {
        return repositorioCentroSalud.save(centroSalud);
    }

    @Override
    @Transactional
    public CentroSalud modificar(CentroSalud centroSalud) {
        return repositorioCentroSalud.save(centroSalud);
    }

    @Override
    @Transactional
    public void eliminar(CentroSalud centroSalud) {
        repositorioCentroSalud.delete(centroSalud);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CentroSalud> buscarPorId(Long id) {
        return repositorioCentroSalud.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroSalud> buscarPorNombre(String nombre) {
        return repositorioCentroSalud.searchByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CentroSalud> buscarPorLocalidad(String localidad) {
        return repositorioCentroSalud.searchByLocalidad(localidad);
    }
}
