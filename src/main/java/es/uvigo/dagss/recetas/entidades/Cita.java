package es.uvigo.dagss.recetas.entidades;

import java.util.Date;
//import jakarta.*;
import jakarta.persistence.*;

@Entity
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente pacienteCitado;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medicoAtendera;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    private int duracion = 15; // Por defecto 15 minutos

    @Enumerated(EnumType.STRING)
    private EstadoCita estado;

    // Constructor, getters y setters

    public Cita() {
        // Constructor por defecto
    }

    public Cita(Paciente pacienteCitado, Medico medicoAtendera, Date fechaHora, int duracion, EstadoCita estado) {
        this.pacienteCitado = pacienteCitado;
        this.medicoAtendera = medicoAtendera;
        this.fechaHora = fechaHora;
        this.duracion = duracion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPacienteCitado() {
        return pacienteCitado;
    }

    public void setPacienteCitado(Paciente pacienteCitado) {
        this.pacienteCitado = pacienteCitado;
    }

    public Medico getMedicoAtendera() {
        return medicoAtendera;
    }

    public void setMedicoAtendera(Medico medicoAtendera) {
        this.medicoAtendera = medicoAtendera;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }
}
