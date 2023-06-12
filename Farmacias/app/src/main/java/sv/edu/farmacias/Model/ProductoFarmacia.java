package sv.edu.farmacias.Model;

public class ProductoFarmacia {
    private int idProducto;
    private int idFarmacia;
    private int disponibilidad;
    private double precioActual;
    private double precioNormal;

    public ProductoFarmacia(int idProducto, int idFarmacia, int disponibilidad, double precioActual, double precioNormal) {
        this.idProducto = idProducto;
        this.idFarmacia = idFarmacia;
        this.disponibilidad = disponibilidad;
        this.precioActual = precioActual;
        this.precioNormal = precioNormal;
    }


    // Getters
    public int getIdProducto() {
        return idProducto;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public double getPrecioActual() {
        return precioActual;
    }

    public double getPrecioNormal() {
        return precioNormal;
    }

    // Setters
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setPrecioActual(double precioActual) {
        this.precioActual = precioActual;
    }

    public void setPrecioNormal(double precioNormal) {
        this.precioNormal = precioNormal;
    }
}
