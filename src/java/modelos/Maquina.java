package modelos;

public class Maquina {
    private int id;
    private String modelo;
    private String tipo;
    private int capacidad;
    private int anho;
    private boolean estado;

    // Constructor
    public Maquina(int id, String modelo, String tipo, int capacidad, int anho, boolean estado) {
        this.id = id;
        this.modelo = modelo;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.anho = anho;
        this.estado = estado;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getAnho() {
        return anho;
    }

    public void setAnho(int anho) {
        this.anho = anho;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
