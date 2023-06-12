package sv.edu.farmacias.Model;

public class Farmacia {
    private Integer Codigo;

    private String nombre;
    private String telefono;
    private double latitud;
    private double longitud;

    public Farmacia(Integer codigo, String nombre, String telefono, double latitud, double longitud) {
        this.Codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public Integer getCodigo(){
        return Codigo;
    }

    public void setCodigo(Integer codigo){
        Codigo = codigo;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
