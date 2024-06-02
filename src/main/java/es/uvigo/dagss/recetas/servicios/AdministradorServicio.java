package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Administrador;

import java.util.List;
import java.util.Optional;

public interface AdministradorServicio {

    public List<Administrador> obtenerAdmins();

    public Administrador crear(Administrador admin);

    public Administrador modificar(Administrador admin);

    public void eliminar(Administrador admin);

    public Optional<Administrador> buscarPorId(Long id);

}
