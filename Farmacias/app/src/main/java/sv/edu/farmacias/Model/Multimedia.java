package sv.edu.farmacias.Model;

public class Multimedia {
    private int codigo;
    private String url;
    private boolean esPrincipal;
    private int orden;

    // Getters
    public int getCodigo() {
        return codigo;
    }

    public String getUrl() {
        return url;
    }

    public boolean getEsPrincipal() {
        return esPrincipal;
    }

    public int getOrden() {
        return orden;
    }

    // Setters
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
