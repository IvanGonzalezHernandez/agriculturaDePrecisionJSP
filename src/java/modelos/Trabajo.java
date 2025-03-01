package modelos;

import java.util.Date;

public class Trabajo {
    private int idTrabajo;
    private int idAgricultor;
    private String tipo;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idMaquina;  // Puede ser NULL
    private Integer idMaquinista;  // Puede ser NULL
    private int idParcela;
    private String estado;

    // Constructor
    public Trabajo(int idTrabajo, int idAgricultor, String tipo, Date fechaInicio, Date fechaFin,
                   Integer idMaquina, Integer idMaquinista, int idParcela, String estado) {
        this.idTrabajo = idTrabajo;
        this.idAgricultor = idAgricultor;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idMaquina = idMaquina;
        this.idMaquinista = idMaquinista;
        this.idParcela = idParcela;
        this.estado = estado;
    }

    // Getters y setters
    public int getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(int idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public int getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public Integer getIdMaquinista() {
        return idMaquinista;
    }

    public void setIdMaquinista(Integer idMaquinista) {
        this.idMaquinista = idMaquinista;
    }

    public int getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(int idParcela) {
        this.idParcela = idParcela;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
