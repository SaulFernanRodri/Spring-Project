package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.repositorios.MedicamentoDAO;
import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoServicioImpl implements MedicamentoServicio {

    private final MedicamentoDAO repositorioMedicamento;

    @Autowired
    public MedicamentoServicioImpl(MedicamentoDAO repositorioMedicamento) {
        this.repositorioMedicamento = repositorioMedicamento;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> obtenerTodos() {
        return repositorioMedicamento.findAll();
    }

    @Override
    @Transactional
    public Medicamento crear(Medicamento medicamento) {
        return repositorioMedicamento.save(medicamento);
    }

    @Override
    @Transactional
    public Medicamento modificar(Medicamento medicamento) {
        return repositorioMedicamento.save(medicamento);
    }

    @Override
    @Transactional
    public void eliminar(Medicamento medicamento) {
        repositorioMedicamento.delete(medicamento);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medicamento> buscarPorId(Long id) {
        return repositorioMedicamento.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarPorNombreComercial(String nombreComercial) {
        return repositorioMedicamento.searchByNombreComercial(nombreComercial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarPorPrincipioActivo(String principioActivo) {
        return repositorioMedicamento.searchByPrincipioActivo(principioActivo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarPorFabricante(String fabricante) {
        return repositorioMedicamento.searchByFabricante(fabricante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarPorFamilia(String familia) {
        return repositorioMedicamento.searchByFamilia(familia);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> buscarMedicamentos(String nombreComercial, String principioActivo, String fabricante, String familia) {
        return repositorioMedicamento.buscarMedicamentos(nombreComercial, principioActivo, fabricante, familia);
    }

}
