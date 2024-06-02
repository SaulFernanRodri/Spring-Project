package es.uvigo.dagss.recetas.servicios;

import es.uvigo.dagss.recetas.repositorios.RecetaDAO;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Receta;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import es.uvigo.dagss.recetas.entidades.EstadoReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RecetaServicioImpl implements RecetaServicio {

    private final RecetaDAO recetaDAO;

    @Autowired
    public RecetaServicioImpl(RecetaDAO recetaDAO) {
        this.recetaDAO = recetaDAO;
    }

    @Override
    @Transactional
    public List<Receta> generarPlanDeRecetas(Prescripcion prescripcion) {
        List<Receta> planDeRecetas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // Obtener la duración total de la prescripción en días
        long duracion = (prescripcion.getFechaFin().getTime() - prescripcion.getFechaInicio().getTime()) / (24 * 60 * 60 * 1000);
        int totalDosis = (int) (duracion * prescripcion.getDosisDiaria()); // Total de dosis necesarias
        int totalCajas = (totalDosis + prescripcion.getMedicamentoPrescrito().getNumeroDosis() - 1) / prescripcion.getMedicamentoPrescrito().getNumeroDosis(); // Redondeo hacia arriba

        calendar.setTime(prescripcion.getFechaInicio());

        for (int i = 0; i < totalCajas; i++) {
            Receta receta = new Receta();
            receta.setPrescripcion(prescripcion);
            receta.setNumeroUnidades(1); // Una caja por receta
            receta.setEstado(EstadoReceta.PLANIFICADA);

            // Fecha de validez inicial (una semana antes, excepto la primera receta)
            Date fechaValidezInicial = calendar.getTime();
            if (i > 0) {
                calendar.add(Calendar.DATE, -7);
                fechaValidezInicial = calendar.getTime();
            }
            receta.setFechaValidezInicial(fechaValidezInicial);

            // Fecha de validez final (una semana después)
            calendar.add(Calendar.DATE, (int) (7 + (prescripcion.getMedicamentoPrescrito().getNumeroDosis() / prescripcion.getDosisDiaria())));
            Date fechaValidezFinal = calendar.getTime();
            calendar.add(Calendar.DATE, 7); // Preparar para la siguiente iteración
            receta.setFechaValidezFinal(fechaValidezFinal);

            planDeRecetas.add(receta);
            recetaDAO.save(receta); // Guardar la receta
        }

        return planDeRecetas;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Receta> obtenerRecetasPendientesPorPaciente(Long pacienteId) {
        return recetaDAO.findByPrescripcionPacienteIdAndEstadoOrderByFechaValidezInicialAsc(pacienteId, EstadoReceta.PLANIFICADA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Receta> obtenerRecetasPendientesPorPaciente(String numeroTarjetaSanitaria) {
        return recetaDAO.findByPrescripcionPacienteNumeroTarjetaSanitariaAndEstadoOrderByFechaValidezInicialAsc(numeroTarjetaSanitaria, EstadoReceta.PLANIFICADA);
    }

    @Override
    @Transactional
    public Receta marcarRecetaComoServida(Long recetaId, Farmacia farmaciaServidora) {
        Receta receta = recetaDAO.findById(recetaId).orElse(null);
        if (receta != null && receta.getEstado().equals(EstadoReceta.PLANIFICADA) &&
                receta.getFechaValidezInicial().before(new Date()) &&
                receta.getFechaValidezFinal().after(new Date())) {
            receta.setEstado(EstadoReceta.SERVIDA);
            receta.setFarmaciaServidora(farmaciaServidora);
            return recetaDAO.save(receta);
        } else {
            throw new IllegalStateException("La receta no puede ser servida.");
        }
    }



}