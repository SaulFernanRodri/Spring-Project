package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Medicamento;

import java.util.List;
import java.util.Optional;

public interface MedicamentoServicio {

    List<Medicamento> obtenerTodos();

    Medicamento crear(Medicamento medicamento);

    Medicamento modificar(Medicamento medicamento);

    void eliminar(Medicamento medicamento);

    Optional<Medicamento> buscarPorId(Long id);

    List<Medicamento> buscarPorNombreComercial(String nombreComercial);

    List<Medicamento> buscarPorPrincipioActivo(String principioActivo);

    List<Medicamento> buscarPorFabricante(String fabricante);

    List<Medicamento> buscarPorFamilia(String familia);

    List<Medicamento> buscarMedicamentos(String nombreComercial, String principioActivo, String fabricante, String familia);

}
