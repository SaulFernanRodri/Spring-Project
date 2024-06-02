package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "FARMACIA")
public class Farmacia extends Usuario {

    // Anadir atributos propios
    private String nombreEstablecimiento;

    private String nombreFarmaceutico;

    private String apellidosFarmaceutico;

    private String dniNif;

    private String numeroColegiadoFarmaceutico;

    @Embedded
    private Direccion direccion;

    private String telefono;

    private String email;

    public Farmacia() {
        super(TipoUsuario.FARMACIA);
    }

    // Constructor con todos los atributos
    public Farmacia(String nombreEstablecimiento, String nombreFarmaceutico, String apellidosFarmaceutico,
                    String dniNif, String numeroColegiadoFarmaceutico, Direccion direccion, String telefono,
                    String email, Boolean activo) {
        super(TipoUsuario.FARMACIA);
        this.nombreEstablecimiento = nombreEstablecimiento;
        this.nombreFarmaceutico = nombreFarmaceutico;
        this.apellidosFarmaceutico = apellidosFarmaceutico;
        this.dniNif = dniNif;
        this.numeroColegiadoFarmaceutico = numeroColegiadoFarmaceutico;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y setters

    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }

    public String getNombreFarmaceutico() {
        return nombreFarmaceutico;
    }

    public void setNombreFarmaceutico(String nombreFarmaceutico) {
        this.nombreFarmaceutico = nombreFarmaceutico;
    }

    public String getApellidosFarmaceutico() {
        return apellidosFarmaceutico;
    }

    public void setApellidosFarmaceutico(String apellidosFarmaceutico) {
        this.apellidosFarmaceutico = apellidosFarmaceutico;
    }

    public String getDniNif() {
        return dniNif;
    }

    public void setDniNif(String dniNif) {
        this.dniNif = dniNif;
    }

    public String getNumeroColegiadoFarmaceutico() {
        return numeroColegiadoFarmaceutico;
    }

    public void setNumeroColegiadoFarmaceutico(String numeroColegiadoFarmaceutico) {
        this.numeroColegiadoFarmaceutico = numeroColegiadoFarmaceutico;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
