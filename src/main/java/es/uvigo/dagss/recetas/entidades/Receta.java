package es.uvigo.dagss.recetas.entidades;

import es.uvigo.dagss.recetas.entidades.EstadoReceta;
import es.uvigo.dagss.recetas.entidades.Farmacia;
import es.uvigo.dagss.recetas.entidades.Prescripcion;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescripcion_id", nullable = false)
    private Prescripcion prescripcion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaValidezInicial;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaValidezFinal;

    private int numeroUnidades;

    @Enumerated(EnumType.STRING)
    private EstadoReceta estado;

    @ManyToOne
    @JoinColumn(name = "farmacia_id")
    private Farmacia farmaciaServidora;

    public Receta() {
        // Constructor por defecto
    }

    public Receta(Prescripcion prescripcion, Date fechaValidezInicial, Date fechaValidezFinal,
                  int numeroUnidades, EstadoReceta estado) {
        this.prescripcion = prescripcion;
        this.fechaValidezInicial = fechaValidezInicial;
        this.fechaValidezFinal = fechaValidezFinal;
        this.numeroUnidades = numeroUnidades;
        this.estado = estado;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prescripcion getPrescripcion() {
        return prescripcion;
    }

    public void setPrescripcion(Prescripcion prescripcion) {
        this.prescripcion = prescripcion;
    }

    public Date getFechaValidezInicial() {
        return fechaValidezInicial;
    }

    public void setFechaValidezInicial(Date fechaValidezInicial) {
        this.fechaValidezInicial = fechaValidezInicial;
    }

    public Date getFechaValidezFinal() {
        return fechaValidezFinal;
    }

    public void setFechaValidezFinal(Date fechaValidezFinal) {
        this.fechaValidezFinal = fechaValidezFinal;
    }

    public int getNumeroUnidades() {
        return numeroUnidades;
    }

    public void setNumeroUnidades(int numeroUnidades) {
        this.numeroUnidades = numeroUnidades;
    }

    public EstadoReceta getEstado() {
        return estado;
    }

    public void setEstado(EstadoReceta estado) {
        this.estado = estado;
    }

    public Farmacia getFarmaciaServidora() {
        return farmaciaServidora;
    }

    public void setFarmaciaServidora(Farmacia farmaciaServidora) {
        this.farmaciaServidora = farmaciaServidora;
    }
}