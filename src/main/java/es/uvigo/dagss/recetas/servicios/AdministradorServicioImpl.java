package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.repositorios.AdministradorDAO;
import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class AdministradorServicioImpl implements AdministradorServicio{

    private final AdministradorDAO repositorioAdministrador;

    @Autowired
    public AdministradorServicioImpl(AdministradorDAO repositorioAdministrador) {
        this.repositorioAdministrador = repositorioAdministrador;
    }

    public List<Administrador> obtenerAdmins() {
        return repositorioAdministrador.findAll();
    }

    public Administrador crear(Administrador admin) {
        return repositorioAdministrador.save(admin);
    }

    @Override
    @Transactional
    public Administrador modificar(Administrador admin) {
        return repositorioAdministrador.save(admin);
    }

    @Override
    @Transactional
    public void eliminar(Administrador admin) {
        repositorioAdministrador.delete(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Administrador> buscarPorId(Long id) {
        return repositorioAdministrador.findById(id);
    }


}
