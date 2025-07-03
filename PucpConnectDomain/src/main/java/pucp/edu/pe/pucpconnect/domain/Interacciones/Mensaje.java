/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.domain.Interacciones;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import java.time.LocalDateTime;

/**
 *
 * @author Irico
 */
public class Mensaje {
    private int idMensaje;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean estado;
    private Alumno emisor;
    private Alumno receptor;

    public Mensaje() {}
    public Mensaje(int id,String contenido,
                   LocalDateTime fechaEnvio,boolean estado,
                   Alumno emisor,Alumno receptor) {
        this.idMensaje = id;
        this.contenido = contenido;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public void editarContenido(String nuevo) {
        if (nuevo == null || nuevo.isBlank())
            throw new IllegalArgumentException("Contenido inválido");
        this.setContenido(nuevo);
    }

    public void eliminar() {
        this.setEstado(false);
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Alumno getEmisor() {
        return emisor;
    }

    public void setEmisor(Alumno emisor) {
        this.emisor = emisor;
    }

    public Alumno getReceptor() {
        return receptor;
    }

    public void setReceptor(Alumno receptor) {
        this.receptor = receptor;
    }
}
