package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.repositorios.PrescripcionDAO;
import es.uvigo.dagss.recetas.repositorios.RecetaDAO;
import es.uvigo.dagss.recetas.entidades.EstadoReceta;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import es.uvigo.dagss.recetas.entidades.EstadoPrescripcion;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PrescripcionServicioImpl implements PrescripcionServicio {

    private final PrescripcionDAO repositorioPrescripcion;

    private final RecetaDAO repositorioReceta;

    private final RecetaServicio recetaServicio;


    @Autowired
    public PrescripcionServicioImpl(PrescripcionDAO repositorioPrescripcion, RecetaDAO repositorioReceta, RecetaServicio recetaServicio) {
        this.repositorioPrescripcion = repositorioPrescripcion;
        this.repositorioReceta = repositorioReceta;
        this.recetaServicio = recetaServicio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prescripcion> obtenerPrescripcionesVigentesPorPaciente(Long pacienteId) {
        Date fechaActual = new Date();
        return repositorioPrescripcion.findPrescripcionesVigentesPorPaciente(pacienteId, fechaActual);
    }

    @Override
    @Transactional
    public Prescripcion anularPrescripcion(Long prescripcionId) {
        Optional<Prescripcion> prescripcionOpt = repositorioPrescripcion.findById(prescripcionId);
        if (prescripcionOpt.isPresent()) {
            Prescripcion prescripcion = prescripcionOpt.get();
            prescripcion.setEstado(EstadoPrescripcion.INACTIVO);

            List<Receta> recetas = repositorioReceta.findByPrescripcionId(prescripcionId);
            for (Receta receta : recetas) {
                receta.setEstado(EstadoReceta.ANULADA);
                repositorioReceta.save(receta);
            }

            return repositorioPrescripcion.save(prescripcion);
        } else {
            throw new ResourceNotFoundException("Prescripción no encontrada con ID: " + prescripcionId);
        }
    }

    @Override
    @Transactional
    public Prescripcion crearPrescripcion(Prescripcion prescripcion) {
        // Asignar la fecha actual como fecha de inicio de la prescripción
        prescripcion.setFechaInicio(new Date());
        prescripcion.setEstado(EstadoPrescripcion.ACTIVO);

        // Guardar la prescripción en la base de datos
        Prescripcion nuevaPrescripcion = repositorioPrescripcion.save(prescripcion);

        // Generar el plan de recetas para la nueva prescripción
        List<Receta> planDeRecetas = recetaServicio.generarPlanDeRecetas(nuevaPrescripcion);

        // Guardar cada receta del plan en la base de datos
        for (Receta receta : planDeRecetas) {
            repositorioReceta.save(receta);
        }

        return nuevaPrescripcion;
    }


}