package sv.edu.farmacias.Model;

import java.util.List;

public class Producto {
    private int codigo;
    private String nombre;
    private List<ProductoFarmacia> productoFarmacia;
    private List<Multimedia> multimedia;

    // Constructor
    public Producto(int codigo, String nombre,  List<ProductoFarmacia> productoFarmacia, List<Multimedia> multimedia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.productoFarmacia = productoFarmacia;
        this.multimedia = multimedia;
    }

    // Getters
    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }


    public List<ProductoFarmacia> getProductoFarmacia() {
        return productoFarmacia;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    // Setters
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public void setProductoFarmacia(List<ProductoFarmacia> productoFarmacia) {
        this.productoFarmacia = productoFarmacia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }
}
