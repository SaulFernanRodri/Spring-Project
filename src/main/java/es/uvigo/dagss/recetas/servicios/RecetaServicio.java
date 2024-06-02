package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.entidades.Prescripcion;

import java.util.List;

public interface RecetaServicio {

    List<Receta> generarPlanDeRecetas(Prescripcion prescripcion);
    List<Receta> obtenerRecetasPendientesPorPaciente(Long pacienteId);
    List<Receta> obtenerRecetasPendientesPorPaciente(String numeroTarjetaSanitaria);
    Receta marcarRecetaComoServida(Long recetaId, Farmacia farmaciaServidora);


}