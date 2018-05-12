package co.com.gguatibonza.redes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Archivo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("extension")
    @Expose
    private String extension;
    @SerializedName("ruta")
    @Expose
    private String ruta;
    @SerializedName("fechaSubida")
    @Expose
    private String fechaSubida;
    @SerializedName("ultimaModificacion")
    @Expose
    private String ultimaModificacion;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public Archivo() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(String ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }
}
