/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.domain.Social;

/**
 *
 * @author Irico
 */
import java.sql.Timestamp;

public class Story {
    private int idStory;
    private int usuarioId;
    private String urlContenido;
    private String tipo;           // "IMAGEN" o "VIDEO"
    private boolean estado;
    private Timestamp fechaCreacion;

    public Story() {}

    // getters & setters
    public int getIdStory() { return idStory; }
    public void setIdStory(int idStory) { this.idStory = idStory; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getUrlContenido() { return urlContenido; }
    public void setUrlContenido(String urlContenido) { this.urlContenido = urlContenido; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public Timestamp getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Timestamp fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}