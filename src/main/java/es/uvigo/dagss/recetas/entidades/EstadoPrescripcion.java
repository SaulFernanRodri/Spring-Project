package es.uvigo.dagss.recetas.entidades;

public enum EstadoPrescripcion {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO");

    private final String descripcion;

    EstadoPrescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
