package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@DiscriminatorValue(value = "PACIENTE")
public class Paciente extends Usuario {

    // Atributos específicos de Paciente
    private String nombre;
    private String apellidos;
    private String dni;
    @Column(name = "tarjeta_sanitaria")
    private String numeroTarjetaSanitaria;
    @Column(name = "seguridad_social")
    private String numeroSeguridadSocial;
    private String direccion;
    private String telefono;
    private String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "centro_salud_id")
    private CentroSalud centroSalud;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medicoAsignado;

    // Atributo de activo heredado de Usuario

    // Constructores
    public Paciente() {
        super(TipoUsuario.PACIENTE);
    }

    public Paciente(String nombre, String apellidos, String dni, String numeroTarjetaSanitaria,
                    String numeroSeguridadSocial, String direccion, String telefono, String email,
                    Date fechaNacimiento, CentroSalud centroSalud, Medico medicoAsignado) {
        super(TipoUsuario.PACIENTE);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.numeroTarjetaSanitaria = numeroTarjetaSanitaria;
        this.numeroSeguridadSocial = numeroSeguridadSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.centroSalud = centroSalud;
        this.medicoAsignado = medicoAsignado;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumeroTarjetaSanitaria() {
        return numeroTarjetaSanitaria;
    }

    public void setNumeroTarjetaSanitaria(String numeroTarjetaSanitaria) {
        this.numeroTarjetaSanitaria = numeroTarjetaSanitaria;
    }

    public String getNumeroSeguridadSocial() {
        return numeroSeguridadSocial;
    }

    public void setNumeroSeguridadSocial(String numeroSeguridadSocial) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public CentroSalud getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(CentroSalud centroSalud) {
        this.centroSalud = centroSalud;
    }

    public Medico getMedicoAsignado() {
        return medicoAsignado;
    }

    public void setMedicoAsignado(Medico medicoAsignado) {
        this.medicoAsignado = medicoAsignado;
    }

    public Boolean getActivo() {
        return super.getActivo();
    }

    public void setActivo(Boolean activo) {
        super.setActivo(activo);
    }




}