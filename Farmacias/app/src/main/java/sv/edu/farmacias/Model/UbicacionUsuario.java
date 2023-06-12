package sv.edu.farmacias.Model;

public class UbicacionUsuario {
    private double latitud;
    private double longitud;

    private Integer Codigo;
    public UbicacionUsuario(double latitud, double longitud, Integer codigo) {
        this.latitud = latitud;
        this.longitud = longitud;
        Codigo = codigo;
    }
    public Integer getCodigo() {
        return Codigo;
    }
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setCodigo(Integer codigo) {
        this.Codigo = codigo;
    }
}

