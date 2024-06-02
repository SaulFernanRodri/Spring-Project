package es.uvigo.dagss.recetas.entidades;
//import jakarta.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Prescripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamentoPrescrito;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private Double dosisDiaria;

    @Column(nullable = false)
    private String indicaciones;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPrescripcion estado;

    public Prescripcion() {
        this.fechaInicio = new Date();
        this.estado = EstadoPrescripcion.ACTIVO;
    }

    // Constructor con parámetros
    public Prescripcion(Medicamento medicamentoPrescrito, Paciente paciente, Medico medico, Double dosisDiaria,
                        String indicaciones, Date fechaFin) {
        this.medicamentoPrescrito = medicamentoPrescrito;
        this.paciente = paciente;
        this.medico = medico;
        this.dosisDiaria = dosisDiaria;
        this.indicaciones = indicaciones;
        this.fechaInicio = new Date();
        this.fechaFin = fechaFin;
        this.estado = EstadoPrescripcion.ACTIVO;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicamento getMedicamentoPrescrito() {
        return medicamentoPrescrito;
    }

    public void setMedicamentoPrescrito(Medicamento medicamentoPrescrito) {
        this.medicamentoPrescrito = medicamentoPrescrito;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Double getDosisDiaria() {
        return dosisDiaria;
    }

    public void setDosisDiaria(Double dosisDiaria) {
        this.dosisDiaria = dosisDiaria;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoPrescripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrescripcion estado) {
        this.estado = estado;
    }
}
