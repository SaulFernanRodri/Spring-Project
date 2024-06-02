package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ADMINISTRADOR")
public class Administrador extends Usuario {

    private String nombre;
    private String email;

    // Constructor por defecto
    public Administrador() {
        super(TipoUsuario.ADMINISTRADOR);
        this.setActivo(true); // Puedes inicializar el campo activo aquí si siempre será true por defecto
    }

    // Constructor con parámetros
    public Administrador(String login, String password, String nombre, String email, Boolean activo) {
        super(TipoUsuario.ADMINISTRADOR, login, password);
        this.nombre = nombre;
        this.email = email;
        this.setActivo(activo);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

