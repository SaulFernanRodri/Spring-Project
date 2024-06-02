package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.repositorios.FarmaciaDAO;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FarmaciaServicioImpl implements FarmaciaServicio {

    private final FarmaciaDAO repositorioFarmacia;

    @Autowired
    public FarmaciaServicioImpl(FarmaciaDAO repositorioFarmacia) {
        this.repositorioFarmacia = repositorioFarmacia;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Farmacia> obtenerTodas() {
        return repositorioFarmacia.findAll();
    }

    @Override
    @Transactional
    public Farmacia crear(Farmacia farmacia) {
        farmacia.setPassword(farmacia.getNumeroColegiadoFarmaceutico()); // Establecer la contraseña inicial como el número de colegiado
        return repositorioFarmacia.save(farmacia);
    }

    @Override
    @Transactional
    public Farmacia modificar(Farmacia farmacia) {
        return repositorioFarmacia.save(farmacia);
    }

    @Override
    @Transactional
    public void eliminar(Farmacia farmacia) {
        repositorioFarmacia.delete(farmacia);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Farmacia> buscarPorId(Long id) {
        return repositorioFarmacia.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Farmacia> buscarPorNombreEstablecimiento(String nombreEstablecimiento) {
        return repositorioFarmacia.searchByNombreEstablecimiento(nombreEstablecimiento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Farmacia> buscarPorLocalidad(String localidad) {
        return repositorioFarmacia.searchByLocalidad(localidad);
    }

    @Override
    @Transactional
    public Farmacia actualizarPerfilFarmacia(Long farmaciaId, Farmacia datosFarmacia) {
        Optional<Farmacia> farmaciaOpt = repositorioFarmacia.findById(farmaciaId);
        if (farmaciaOpt.isPresent()) {
            Farmacia farmacia = farmaciaOpt.get();
            farmacia.setNombreEstablecimiento(datosFarmacia.getNombreEstablecimiento());
            farmacia.setDireccion(datosFarmacia.getDireccion());
            farmacia.setTelefono(datosFarmacia.getTelefono());
            farmacia.setEmail(datosFarmacia.getEmail());
            return repositorioFarmacia.save(farmacia);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void cambiarContraseña(Long farmaciaId, String nuevaContraseña) {
        Optional<Farmacia> farmaciaOpt = repositorioFarmacia.findById(farmaciaId);
        farmaciaOpt.ifPresent(farmacia -> {
            farmacia.setPassword(nuevaContraseña);
            repositorioFarmacia.save(farmacia);
        });
    }

}
