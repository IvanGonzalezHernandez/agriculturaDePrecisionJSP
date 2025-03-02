package modelos;

public class Parcela {
    private int catastro;
    private int idUsuario;
    private int superficie;

    // Constructor vacío
    public Parcela() {
    }

    // Constructor con parámetros
    public Parcela(int catastro, int idUsuario, int superficie) {
        this.catastro = catastro;
        this.idUsuario = idUsuario;
        this.superficie = superficie;
    }

    // Getters y Setters
    public int getCatastro() {
        return catastro;
    }

    public void setCatastro(int catastro) {
        this.catastro = catastro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

}
