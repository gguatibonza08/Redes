package co.com.gguatibonza.redes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {


    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("contrasena")
    @Expose
    private String contrasena;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("cuenta")
    @Expose
    private String cuenta;
    @SerializedName("fechaRegistro")
    @Expose
    private String fechaRegistro;
    @SerializedName("id")
    @Expose
    private String id;

    public Usuario() {
    }


    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
